package ru.kosenko.coreservice.sensors.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosenko.coreservice.sensors.dtos.AutomationActionDto;
import ru.kosenko.coreservice.sensors.dtos.SensorDataDto;
import ru.kosenko.coreservice.sensors.entities.GreenhouseSettings;
import ru.kosenko.coreservice.sensors.entities.HumidityData;
import ru.kosenko.coreservice.sensors.entities.TemperaturesData;
import ru.kosenko.coreservice.sensors.repositories.GreenhouseSettingsRepository;
import ru.kosenko.coreservice.sensors.repositories.HumidityDataRepository;
import ru.kosenko.coreservice.sensors.repositories.TemperaturesDataRepository;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final TemperaturesDataRepository tempRepo;
    private final HumidityDataRepository humRepo;
    private final GreenhouseSettingsRepository settingsRepo;

    @Transactional
    public AutomationActionDto processData(SensorDataDto dto) {
        // 1. Сохраняем историю
        saveHistory(dto);

        // 2. Получаем настройки автоматики
        GreenhouseSettings settings = settingsRepo.findByGreenhouseId(dto.getGreenhouseId())
                .orElseThrow(() -> new RuntimeException("Настройки не найдены для теплицы: " + dto.getGreenhouseId()));

        // 3. Если авто-режим включен, обновляем состояния
        if (Boolean.TRUE.equals(settings.getIsAutoMode())) {
            updateAutomationState(settings, dto);
            settingsRepo.save(settings);
        }

        // 4. Формируем ответную команду
        return AutomationActionDto.builder()
                .doorOpen(settings.getDoorOpen())
                .windowOpen(settings.getWindowOpen())
                .wateringOn(settings.getWateringOn())
                .build();
    }

    private void saveHistory(SensorDataDto dto) {
        if ("TEMP".equalsIgnoreCase(dto.getType())) {
            tempRepo.save(new TemperaturesData(null, dto.getSensorId(), dto.getValue(), null));
        } else {
            humRepo.save(new HumidityData(null, dto.getSensorId(), dto.getValue(), null));
        }
    }

    private void updateAutomationState(GreenhouseSettings s, SensorDataDto dto) {
        if ("TEMP".equalsIgnoreCase(dto.getType())) {
            // Управление проветриванием
            if (dto.getValue() >= s.getTempOpenWindow()) s.setWindowOpen(true);
            if (dto.getValue() <= s.getTempCloseWindow()) s.setWindowOpen(false);

            // Управление доступом
            if (dto.getValue() >= s.getTempOpenDoor()) s.setDoorOpen(true);
            if (dto.getValue() <= s.getTempCloseDoor()) s.setDoorOpen(false);
        }
        else if ("HUMIDITY".equalsIgnoreCase(dto.getType())) {
            // Полив по датчику (Гистерезис)
            if (dto.getValue() <= s.getMinHumidityPct()) {
                s.setWateringOn(true);
            } else if (dto.getValue() >= s.getMaxHumidityPct()) {
                s.setWateringOn(false);
            }
        }
    }

    @Transactional
    public GreenhouseSettings updateGreenhouseSettings(Long greenhouseId, GreenhouseSettings incoming) {
        GreenhouseSettings existing = settingsRepo.findByGreenhouseId(greenhouseId)
                .orElse(new GreenhouseSettings()); // Создаем новые, если нет

        existing.setGreenhouseId(greenhouseId);
        existing.setTempOpenWindow(incoming.getTempOpenWindow());
        existing.setTempCloseWindow(incoming.getTempCloseWindow());
        existing.setTempOpenDoor(incoming.getTempOpenDoor());
        existing.setTempCloseDoor(incoming.getTempCloseDoor());
        existing.setMinHumidityPct(incoming.getMinHumidityPct());
        existing.setMaxHumidityPct(incoming.getMaxHumidityPct());
        existing.setIsAutoMode(incoming.getIsAutoMode());

        return settingsRepo.save(existing);
    }

}

