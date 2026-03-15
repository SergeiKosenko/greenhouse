package ru.kosenko.coreservice.sensors.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kosenko.coreservice.sensors.dtos.SensorDataDto;
import ru.kosenko.coreservice.sensors.dtos.AutomationActionDto;
import ru.kosenko.coreservice.sensors.entities.GreenHouseSettings;
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
    @PutMapping("/settings/{greenHouseId}")
    public ResponseEntity<GreenHouseSettings> updateSettings(
            @PathVariable Long greenHouseId,
            @RequestBody GreenHouseSettings newSettings) {

        return ResponseEntity.ok(sensorService.updateGreenHouseSettings(greenHouseId, newSettings));
    }

}

