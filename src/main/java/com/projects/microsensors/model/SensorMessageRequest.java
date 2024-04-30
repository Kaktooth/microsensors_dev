package com.projects.microsensors.model;

import java.util.UUID;

public record SensorMessageRequest(UUID id, String message, UUID sensorId, UUID key) {
}
