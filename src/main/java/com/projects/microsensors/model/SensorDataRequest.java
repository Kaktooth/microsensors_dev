package com.projects.microsensors.model;

import java.util.UUID;

public record SensorDataRequest(
    UUID id,
    byte[] data,
    UUID sensorId
) {}
