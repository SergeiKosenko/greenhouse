package ru.kosenko.coreservice.sensors.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kosenko.coreservice.sensors.entities.GreenhouseSettings;
import ru.kosenko.coreservice.sensors.repositories.GreenhouseSettingsRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WateringScheduler {
    private final GreenhouseSettingsRepository settingsRepo;

    @Scheduled(cron = "0 * * * * *") // Запуск каждую минуту в 00 секунд
    @Transactional
    public void checkWateringSchedule() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime().withSecond(0).withNano(0);

        List<GreenhouseSettings> allSettings = settingsRepo.findAll();

        for (GreenhouseSettings s : allSettings) {
            if (s.getWateringStartTime() == null || !Boolean.TRUE.equals(s.getIsAutoMode())) continue;

            // 1. Проверяем, наступило ли время начала полива (с учетом интервала)
            // Для упрощения: если текущее время совпадает с StartTime или StartTime + Interval
            if (isItTimeBySchedule(s, currentTime)) {
                s.setWateringOn(true);
                s.setLastWateringTime(now);
                settingsRepo.save(s);
            }

            // 2. Выключаем полив, если время вышло (LastWateringTime + Duration)
            if (Boolean.TRUE.equals(s.getWateringOn()) && s.getLastWateringTime() != null) {
                if (now.isAfter(s.getLastWateringTime().plusMinutes(s.getWateringDurationMin()))) {
                    s.setWateringOn(false);
                    settingsRepo.save(s);
                }
            }
        }
    }

    private boolean isItTimeBySchedule(GreenhouseSettings s, LocalTime current) {
        LocalTime start = s.getWateringStartTime();
        int interval = s.getWateringIntervalHours();

        // Проверяем все циклы за сутки (например, 06:00, 18:00)
        for (int i = 0; i < 24 / interval; i++) {
            if (start.plusHours(i * interval).equals(current)) return true;
        }
        return false;
    }
}

