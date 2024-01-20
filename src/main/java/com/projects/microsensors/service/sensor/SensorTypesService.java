package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.Placement;
import com.projects.microsensors.model.SensorType;
import com.projects.microsensors.repository.PlacementRepository;
import com.projects.microsensors.repository.SensorTypesRepository;
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
public class SensorTypesService {
    private final SensorTypesRepository sensorTypesRepository;
    private final PlacementRepository placementRepository;

    public List<SensorType> getSensorTypes() {
        return sensorTypesRepository.findAll();
    }

    public List<Placement> getPlacementTypes() {
        return placementRepository.findAll();
    }

    public List<SensorType> getAllSensorTypes(Iterable<UUID> ids) {
        return sensorTypesRepository.findAllById(ids);
    }

    public List<Placement> getAllPlacementTypes(Iterable<UUID> ids) {
        return placementRepository.findAllById(ids);
    }
}
