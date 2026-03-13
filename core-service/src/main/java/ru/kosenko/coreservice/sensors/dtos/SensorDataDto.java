package ru.kosenko.coreservice.sensors.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorDataDto {
    private Long greenhouseId;
    private Long sensorId; // id из таблиц temperatures или humidity
    private Float value;
    private String type;   // "TEMP" или "HUMIDITY"
}
