package com.projects.microsensors.model;

import org.springframework.context.ApplicationEvent;

public class UpdateEvent extends ApplicationEvent {
    public UpdateEvent(Object source) {
        super(source);
    }
}
