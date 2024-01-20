package com.projects.microsensors.repository;

import com.projects.microsensors.model.User;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends CommonRepository<User> {
    boolean existsUserByUsername(String username);

    User findUserByUsername(String username);
}