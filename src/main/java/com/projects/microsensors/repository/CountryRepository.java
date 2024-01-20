package com.projects.microsensors.repository;

import com.projects.microsensors.model.Country;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CountryRepository extends CommonRepository<Country> {
}