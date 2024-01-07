package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.SensorData;
import com.projects.microsensors.model.SensorDataRequest;
import com.projects.microsensors.repository.SensorDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SensorDataService {
    private final SensorDataRepository sensorDataRepository;

    public void saveSensorData(SensorDataRequest sensorDataRequest) {
        UUID id = sensorDataRequest.id();
        if (id == null) {
            id = UUID.randomUUID();
        }
        log.info(new String(sensorDataRequest.data()));
        log.info(Utf8.decode(sensorDataRequest.data()));
        SensorData sensor = SensorData.builder()
                .id(id)
                .receiveDate(Timestamp.from(Instant.now()))
                .data(sensorDataRequest.data())
                .sensorId(sensorDataRequest.sensorId())
                .build();

        sensorDataRepository.save(sensor);
    }
}
