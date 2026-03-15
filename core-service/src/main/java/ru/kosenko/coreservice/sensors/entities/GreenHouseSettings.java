package ru.kosenko.coreservice.sensors.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "green_house_settings")
public class GreenHouseSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "green_house_id", unique = true)
    private Long greenHouseId;

    // Температура
    private Float tempOpenWindow;
    private Float tempCloseWindow;
    private Float tempOpenDoor;
    private Float tempCloseDoor;

    // Влажность
    private Float minHumidityPct;
    private Float maxHumidityPct;

    // Температура подогрева
    private Float tempTurnOnHeater;
    private Float tempTurnOffHeater;

    // Расписание
    private LocalTime wateringStartTime;
    private Integer wateringIntervalHours;
    private Integer wateringDurationMin;

    // Состояния
    private Boolean windowOpen = false;
    private Boolean doorOpen = false;
    private Boolean wateringOn = false;
    private Boolean heaterOn = false;

    private LocalDateTime lastWateringTime;
    private Boolean isAutoMode = true;
}



