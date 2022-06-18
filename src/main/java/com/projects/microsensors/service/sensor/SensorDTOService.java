package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorDTO;
import com.projects.microsensors.model.SensorData;
import com.projects.microsensors.model.SensorMessage;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.repository.SensorDataRepository;
import com.projects.microsensors.repository.SensorMessageRepository;
import com.projects.microsensors.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class SensorDTOService {

    private final SensorRepository sensorRepository;
    private final SensorDataRepository sensorDataRepository;
    private final SensorMessageRepository sensorMessageRepository;

    @Autowired
    public SensorDTOService(SensorRepository sensorRepository,
                            SensorDataRepository sensorDataRepository,
                            SensorMessageRepository sensorMessageRepository) {
        this.sensorRepository = sensorRepository;
        this.sensorDataRepository = sensorDataRepository;
        this.sensorMessageRepository = sensorMessageRepository;
    }

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
        int limitSize = 5;
        int skipDataSize = sensorData.size() - limitSize;
        int skipMessageSize = sensorMessages.size() - limitSize;
        sensorData = sensorData.stream().sorted().skip(skipDataSize).toList();
        sensorMessages = sensorMessages.stream().sorted().skip(skipMessageSize).toList();
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
