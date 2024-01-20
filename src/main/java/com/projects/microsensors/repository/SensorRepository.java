package com.projects.microsensors.repository;

import com.projects.microsensors.model.Sensor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface SensorRepository extends CommonRepository<Sensor> {

    List<Sensor> findAllByPersonal(Boolean isPersonal);

    List<Sensor> findAllByUserId(UUID userId);
}
