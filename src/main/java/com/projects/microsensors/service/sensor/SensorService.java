package com.projects.microsensors.service.sensor;

import com.opencsv.CSVWriter;
import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor saveSensor(SensorRequest sensorRequest) {
        UUID id = sensorRequest.getId();
        if (id == null) {
            id = UUID.randomUUID();
        }

        Sensor sensor = Sensor.builder()
            .id(id)
            .name(sensorRequest.getName())
            .sensorInfo(sensorRequest.getSensorInfo())
            .build();

        return sensorRepository.save(sensor);
    }

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }
}
