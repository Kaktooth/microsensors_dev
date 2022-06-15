package com.projects.microsensors.repository;

import com.projects.microsensors.model.SensorData;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface SensorDataRepository extends CommonRepository<SensorData> {

    List<SensorData> findAllBySensorId(UUID sensorId);
}
