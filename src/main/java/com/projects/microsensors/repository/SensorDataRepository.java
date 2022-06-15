package com.projects.microsensors.repository;

import com.projects.microsensors.model.SensorData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SensorDataRepository extends CommonRepository<SensorData> {

    List<SensorData> findAllBySensorId(UUID sensorId);
}
