package com.projects.microsensors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorDTO {
    UUID id;
    String name;
    String sensorInfo;
    List<SensorData> sensorData;
    List<SensorMessage> sensorMessages;
}
