package com.projects.microsensors.model;

import java.sql.Timestamp;
import java.util.UUID;

public record SensorMessageRequest(UUID id, Timestamp receiveDate, String message, UUID sensorId) {
}
