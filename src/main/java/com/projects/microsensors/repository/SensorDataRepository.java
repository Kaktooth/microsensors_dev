package com.projects.microsensors.repository;

import com.projects.microsensors.model.SensorData;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.UUID;

@RestResource
public interface SensorDataRepository extends CommonRepository<SensorData> {

    List<SensorData> findAllBySensorId(UUID sensorId);
}
