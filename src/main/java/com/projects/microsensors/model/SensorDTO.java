package com.projects.microsensors.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class SensorDTO {
    UUID id;
    String name;
    String sensorInfo;
    List<SensorData> sensorData;
}
