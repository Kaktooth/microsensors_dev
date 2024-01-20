package com.projects.microsensors.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequest {

    UUID id;

    List<UUID> sensorTypes;

    List<UUID> placements;

    List<SensorType> sensorTypesConverted;

    List<Placement> placementsConverted;

    Country country;

    Boolean personal;

    List<SensorData> sensorData;

    List<SensorMessage> sensorMessages;

    public SensorRequest(List<UUID> sensorTypes, List<UUID> placements, Country country,
                         Boolean personal, List<SensorData> sensorData, List<SensorMessage> sensorMessages) {
        this.id = UUID.randomUUID();
        this.sensorTypes = sensorTypes;
        this.placements = placements;
        this.country = country;
        this.personal = personal;
        this.sensorData = sensorData;
        this.sensorMessages = sensorMessages;
    }
}