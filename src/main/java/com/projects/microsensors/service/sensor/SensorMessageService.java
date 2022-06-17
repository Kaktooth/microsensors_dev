package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorMessage;
import com.projects.microsensors.model.SensorMessageRequest;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.repository.SensorMessageRepository;
import com.projects.microsensors.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class SensorMessageService {

    private final SensorMessageRepository sensorMessageRepository;

    @Autowired
    public SensorMessageService(SensorMessageRepository sensorMessageRepository) {
        this.sensorMessageRepository = sensorMessageRepository;
    }

    public void saveSensorMessage(SensorMessageRequest messageRequest) {
        UUID id = messageRequest.id();
        if (id == null) {
            id = UUID.randomUUID();
        }
        SensorMessage message = SensorMessage.builder()
            .id(id)
            .receiveDate(Timestamp.from(Instant.now()))
            .message(messageRequest.message())
            .sensorId(messageRequest.sensorId())
            .build();

        sensorMessageRepository.save(message);
    }
}
