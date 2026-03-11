package ru.kosenko.coreservice.sensors.dtos;

import lombok.Data;

@Data
public class SensorDataDto {
    private Long greenhouseId;
    private Long sensorId; // id из таблиц temperatures или humidity
    private Float value;
    private String type;   // "TEMP" или "HUMIDITY"
}
