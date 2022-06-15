package com.projects.microsensors.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RepositoryEventHandler(SensorData.class)
public class SensorDataEventHandler {

    @HandleAfterCreate
    public String handleAuthorAfterCreate(SensorData data) {
        log.info("After create ....");

        return "redirect:/dashboard";
    }
}
