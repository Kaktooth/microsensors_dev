package com.projects.microsensors.model;

import java.util.UUID;

public class SensorRequest {
    private final UUID id;
    private final String name;
    private final String sensorInfo;

    public SensorRequest(
        UUID id,
        String name,
        String sensorInfo) {
        this.id = id;
        this.name = name;
        this.sensorInfo = sensorInfo;
    }
}
