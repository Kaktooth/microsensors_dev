package com.projects.microsensors.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequest {
    UUID id;
    String name;
    String sensorInfo;

    public SensorRequest(
        String name,
        String sensorInfo) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.sensorInfo = sensorInfo;
    }
}