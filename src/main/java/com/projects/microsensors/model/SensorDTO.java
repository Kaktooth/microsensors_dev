package com.projects.microsensors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorDTO {

    UUID id;

    List<SensorType> sensorTypes;

    List<Placement> placements;

    Country country;

    Boolean personal;

    List<SensorData> sensorData;

    List<SensorMessage> sensorMessages;

    UUID userId;

    Integer points;

    public String getSensorMessagesString() {
        String messages = sensorMessages.toString();
        messages = messages
                .replace("[", "")
                .replace("]", "")
                .replace(",", "");
        return messages;
    }

    public String getSensorDataString() {
        String data = sensorData.toString();
        data = data
                .replace("[", "")
                .replace("]", "")
                .replace(",", "");
        return data;
    }
}
