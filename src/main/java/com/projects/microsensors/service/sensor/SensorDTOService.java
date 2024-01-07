package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.*;
import com.projects.microsensors.repository.SensorDataRepository;
import com.projects.microsensors.repository.SensorMessageRepository;
import com.projects.microsensors.repository.SensorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SensorDTOService {

    private final SensorRepository sensorRepository;
    private final SensorDataRepository sensorDataRepository;
    private final SensorMessageRepository sensorMessageRepository;

    public void saveSensor(SensorRequest sensorRequest) {
        UUID id = sensorRequest.getId();
        if (id == null) {
            id = UUID.randomUUID();
        }

        Sensor sensor = Sensor.builder()
                .id(id)
                .name(sensorRequest.getName())
                .sensorInfo(sensorRequest.getSensorInfo())
                .build();

        sensorRepository.save(sensor);
    }

    public SensorDTO getSensorDTO(UUID id) {
        Sensor sensor = sensorRepository.getReferenceById(id);
        List<SensorData> sensorData = sensorDataRepository.findAllBySensorId(id);
        List<SensorMessage> sensorMessages = sensorMessageRepository.findAllBySensorId(id);

        sensorData = sensorData.stream().sorted().skip(Math.max(0, sensorData.size() - 10)).toList();
        sensorMessages = sensorMessages.stream().sorted().skip(Math.max(0, sensorMessages.size() - 10)).toList();
        log.info("get dto: " + id);
        return SensorDTO.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .sensorInfo(sensor.getSensorInfo())
                .sensorData(sensorData)
                .sensorMessages(sensorMessages)
                .build();
    }
}
