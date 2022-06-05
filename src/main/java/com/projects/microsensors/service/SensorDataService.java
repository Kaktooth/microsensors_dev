package com.projects.microsensors.service;

import com.projects.microsensors.model.SensorData;
import com.projects.microsensors.model.SensorDataRequest;
import com.projects.microsensors.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class SensorDataService {
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public void saveSensorData(SensorDataRequest sensorDataRequest) {
        SensorData sensor = SensorData.builder()
            .id(sensorDataRequest.id())
            .receiveDate(Timestamp.from(Instant.now()))
            .data(sensorDataRequest.data())
            .sensorId(sensorDataRequest.sensorId())
            .build();

        sensorDataRepository.save(sensor);
    }
}
