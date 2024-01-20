package com.projects.microsensors.repository;

import com.projects.microsensors.model.SensorType;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SensorTypesRepository extends CommonRepository<SensorType> {
}