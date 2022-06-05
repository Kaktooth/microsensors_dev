package com.projects.microsensors.service;

import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorMessage;
import com.projects.microsensors.model.SensorMessageRequest;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.repository.SensorMessageRepository;
import com.projects.microsensors.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorMessageService {

    private final SensorMessageRepository sensorMessageRepository;

    @Autowired
    public SensorMessageService(SensorMessageRepository sensorMessageRepository) {
        this.sensorMessageRepository = sensorMessageRepository;
    }

    public void saveSensorMessage(SensorMessageRequest messageRequest) {
        SensorMessage message = SensorMessage.builder()
            .id(messageRequest.id())
            .receiveDate(messageRequest.receiveDate())
            .message(messageRequest.message())
            .sensorId(messageRequest.sensorId())
            .build();

        sensorMessageRepository.save(message);
    }
}
