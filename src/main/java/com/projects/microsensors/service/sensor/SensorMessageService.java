package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.SensorMessage;
import com.projects.microsensors.model.SensorMessageRequest;
import com.projects.microsensors.repository.SensorMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class SensorMessageService {

    private final SensorMessageRepository sensorMessageRepository;

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
