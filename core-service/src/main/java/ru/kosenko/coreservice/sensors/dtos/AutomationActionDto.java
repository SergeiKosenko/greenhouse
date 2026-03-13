package ru.kosenko.coreservice.sensors.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AutomationActionDto {
    private Boolean doorOpen;
    private Boolean windowOpen;
    private Boolean wateringOn;
    private Boolean heaterOn;
}
