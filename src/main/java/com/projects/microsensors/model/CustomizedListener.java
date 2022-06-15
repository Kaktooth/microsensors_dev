package com.projects.microsensors.model;

import com.projects.microsensors.controller.sensor.DashboardController;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

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

    @PostLoad
    private void afterLoadInTheDatabase(T object) {
        log.info(" user loaded from database: " + object.getId());
    }
}
