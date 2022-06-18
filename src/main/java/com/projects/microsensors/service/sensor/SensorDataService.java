package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.SensorData;
import com.projects.microsensors.model.SensorDataRequest;
import com.projects.microsensors.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
public class SensorDataService {
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public void saveSensorData(SensorDataRequest sensorDataRequest) {
        UUID id = sensorDataRequest.id();
        if (id == null) {
            id = UUID.randomUUID();
        }
        SensorData sensor = SensorData.builder()
            .id(id)
            .receiveDate(Timestamp.from(Instant.now()))
            .data(Base64.getDecoder().decode(sensorDataRequest.data()))
            .sensorId(sensorDataRequest.sensorId())
            .build();

        sensorDataRepository.save(sensor);
    }
}
