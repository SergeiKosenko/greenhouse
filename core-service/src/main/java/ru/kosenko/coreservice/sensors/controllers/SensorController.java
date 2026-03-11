package ru.kosenko.coreservice.sensors.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kosenko.coreservice.sensors.dtos.SensorDataDto;
import ru.kosenko.coreservice.sensors.dtos.AutomationActionDto;
import ru.kosenko.coreservice.sensors.entities.GreenhouseSettings;
import ru.kosenko.coreservice.sensors.services.SensorService;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping(value = "/data", consumes = "application/json") // Добавили consumes
    public AutomationActionDto receiveData(@RequestBody SensorDataDto dataDto) {
        return sensorService.processData(dataDto);
    }
    @PutMapping("/settings/{greenhouseId}")
    public ResponseEntity<GreenhouseSettings> updateSettings(
            @PathVariable Long greenhouseId,
            @RequestBody GreenhouseSettings newSettings) {

        return ResponseEntity.ok(sensorService.updateGreenhouseSettings(greenhouseId, newSettings));
    }

}

