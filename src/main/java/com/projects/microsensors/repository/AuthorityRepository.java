package com.projects.microsensors.repository;

import com.projects.microsensors.model.Authority;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AuthorityRepository extends CommonRepository<Authority> {

}