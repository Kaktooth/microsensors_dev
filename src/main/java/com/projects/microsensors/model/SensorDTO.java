package com.projects.microsensors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class SensorDTO {
    UUID id;
    String name;
    String sensorInfo;
    List<SensorData> sensorData;
    List<SensorMessage> sensorMessages;
}
