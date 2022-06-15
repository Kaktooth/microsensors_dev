package com.projects.microsensors.repository;

import com.projects.microsensors.model.SensorData;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface SensorDataRepository extends CommonRepository<SensorData> {

    List<SensorData> findAllBySensorId(UUID sensorId);
}
