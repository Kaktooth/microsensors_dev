package com.projects.microsensors.repository;

import com.projects.microsensors.model.SensorMessage;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface SensorMessageRepository extends CommonRepository<SensorMessage> {

    List<SensorMessage> findAllBySensorId(UUID sensorId);
}