package com.projects.microsensors.model;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomizedListener<T extends Domain> {

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdateInsertDatabase(T object) {
        if (object.getId() == null) {
            log.info("About to add a user");
        } else {
            log.info("About to update/delete user: " + object.getId());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdateInTheDataBase(T object) {
        log.info("add or update or delete complete for user: " + object.getId());
    }
}
