package com.projects.microsensors.repository;

import com.projects.microsensors.model.Sensor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource
public interface SensorRepository extends CommonRepository<Sensor> {
}
