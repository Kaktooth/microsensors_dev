package com.projects.microsensors.repository;

import com.projects.microsensors.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

@NoRepositoryBean
public interface CommonRepository<E extends Domain> extends JpaRepository<E, UUID>,
    PagingAndSortingRepository<E, UUID>,
    JpaSpecificationExecutor<E> {
}
