package com.projects.microsensors.repository;

import com.projects.microsensors.model.Key;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface KeyRepository extends CommonRepository<Key> {
    Key findByIp(String ip);

    Key findByKey(UUID key);
    Key findByUserId(UUID userId);
}