package com.projects.microsensors.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SensorDataRequest {
    private final UUID id;
    private final byte[] data;
    private final UUID sensorId;

    public SensorDataRequest(
        UUID id,
        byte[] data,
        UUID sensorId
    ) {
        this.id = id;
        this.data = data;
        this.sensorId = sensorId;
    }
}
