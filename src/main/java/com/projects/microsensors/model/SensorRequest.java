package com.projects.microsensors.model;

import java.util.UUID;

public record SensorRequest(
    UUID id,
    String name,
    String sensorInfo) {
}
