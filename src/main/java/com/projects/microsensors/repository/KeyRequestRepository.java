package com.projects.microsensors.repository;

import com.projects.microsensors.model.KeyRequest;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface KeyRequestRepository extends CommonRepository<KeyRequest> {
    List<KeyRequest> findAllByIdAndRequestDateBetween(UUID id, Timestamp startUsage, Timestamp endUsage);
}
