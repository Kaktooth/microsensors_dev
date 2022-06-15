package com.projects.microsensors.model;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Slf4j
public class CustomizedListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdateInsertDatabase(SensorMessage message) {
        if (message.getId() == null) {
            log.info("About to add a user");
        } else {
            log.info("About to update/delete user: " + message.getId());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdateInTheDataBase(SensorMessage message) {
        log.info("add or update or delete complete for user: " + message.getId());
    }

    @PostLoad
    private void afterLoadInTheDatabase(SensorMessage message) {
        log.info(" user loaded from database: " + message.getId());
    }
}
