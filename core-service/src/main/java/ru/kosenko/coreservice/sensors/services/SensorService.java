package ru.kosenko.coreservice.sensors.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosenko.coreservice.sensors.dtos.AutomationActionDto;
import ru.kosenko.coreservice.sensors.dtos.SensorDataDto;
import ru.kosenko.coreservice.sensors.entities.GreenhouseSettings;
import ru.kosenko.coreservice.sensors.entities.HumidityData;
import ru.kosenko.coreservice.sensors.entities.TemperaturesData;
import ru.kosenko.coreservice.sensors.entities.TemperaturesDoorData;
import ru.kosenko.coreservice.sensors.repositories.GreenhouseSettingsRepository;
import ru.kosenko.coreservice.sensors.repositories.HumidityDataRepository;
import ru.kosenko.coreservice.sensors.repositories.TemperaturesDataRepository;
import ru.kosenko.coreservice.sensors.repositories.TemperaturesDoorDataRepository;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final TemperaturesDataRepository tempRepo;
    private final TemperaturesDoorDataRepository tempDoorRepo;
    private final HumidityDataRepository humRepo;
    private final GreenhouseSettingsRepository settingsRepo;

    @Transactional
    public AutomationActionDto processData(SensorDataDto dto) {
        // 1. Сохраняем историю
        saveHistory(dto);

//        // 2. Ищем сам объект (Теплица или Грядка)
//        Greenhouse greenhouse = greenhouseRepo.findById(dto.getGreenhouseId())
//                .orElseThrow(() -> new RuntimeException("Объект не найден"));

        // 2. Получаем настройки автоматики
        GreenhouseSettings settings = settingsRepo.findByGreenhouseId(dto.getGreenhouseId())
                .orElseThrow(() -> new RuntimeException("Настройки не найдены для теплицы: " + dto.getGreenhouseId()));

        // 3. Если это УЛИЦА (OUTDOOR) — возвращаем "все выключено"
        if ("OUTDOOR".equalsIgnoreCase(dto.getType())) {
            return AutomationActionDto.builder()
                    .doorOpen(false)
                    .windowOpen(false)
                    .wateringOn(settings.getWateringOn())
                    .heaterOn(false)
                    .build();
        }

        // 4. Если авто-режим включен, обновляем состояния
        if (Boolean.TRUE.equals(settings.getIsAutoMode())) {
            updateAutomationState(settings, dto);
            settingsRepo.save(settings);
        }

        // 5. Формируем ответную команду
        return AutomationActionDto.builder()
                .doorOpen(settings.getDoorOpen())
                .windowOpen(settings.getWindowOpen())
                .wateringOn(settings.getWateringOn())
                .heaterOn(settings.getHeaterOn())
                .build();
    }

    private void saveHistory(SensorDataDto dto) {
        if ("TEMP".equalsIgnoreCase(dto.getType())) {
            tempRepo.save(new TemperaturesData(null, dto.getSensorId(), dto.getValue(), null));
        } else if ("HUMIDITY".equalsIgnoreCase(dto.getType())){
            humRepo.save(new HumidityData(null, dto.getSensorId(), dto.getValue(), null));
        } else if ("OUTDOOR".equalsIgnoreCase(dto.getType())){
            tempDoorRepo.save(new TemperaturesDoorData(null, dto.getSensorId(), dto.getValue(), null));
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

            if (dto.getValue() <= s.getTempTurnOnHeater()) {
                s.setHeaterOn(true); // Холодно -> включаем ТЭН
            } else if (dto.getValue() >= s.getTempTurnOffHeater()) {
                s.setHeaterOn(false); // Тепло -> выключаем ТЭН
            }
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

