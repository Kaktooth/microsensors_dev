package com.projects.microsensors.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RepositoryEventHandler(SensorMessage.class)
public class SensorMessageEventHandler extends AbstractRepositoryEventListener<SensorMessage> {

    @HandleAfterCreate
    public String handleAuthorAfterCreate(SensorMessage message) {
        log.info("After create ....");

        return "redirect:/dashboard";
    }
}