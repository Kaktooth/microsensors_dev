package com.projects.microsensors.repository;

import com.projects.microsensors.model.Placement;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PlacementRepository extends CommonRepository<Placement> {
}