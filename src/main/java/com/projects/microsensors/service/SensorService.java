package com.projects.microsensors.service;

import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.repository.SensorRepository;
import org.springframework.stereotype.Service;

@Service
public record SensorService(SensorRepository sensorRepository) {
    public void saveSensor(SensorRequest sensorRequest) {
        Sensor sensor = Sensor.builder()
            .id(sensorRequest.id())
            .name(sensorRequest.name())
            .sensorInfo(sensorRequest.sensorInfo())
            .build();

        sensorRepository.save(sensor);
    }
}
