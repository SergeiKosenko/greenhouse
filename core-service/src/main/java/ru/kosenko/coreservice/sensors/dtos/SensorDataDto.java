package ru.kosenko.coreservice.sensors.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorDataDto {
    private Long greenHouseId;
    private Long sensorId; // id из таблиц temperatures или humidity
    private Float value;
    private String type;   // "TEMP" или "HUMIDITY"
}
