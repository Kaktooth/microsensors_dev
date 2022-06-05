package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorMessageRequest;
import com.projects.microsensors.service.SensorMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/sensor-messages")
public class SensorMessageController {
    private final SensorMessageService sensorMessageService;

    @Autowired
    public SensorMessageController(SensorMessageService sensorMessageService) {
        this.sensorMessageService = sensorMessageService;
    }

    @GetMapping
    public String sensorDataPage() {
        return "Upload message...";
    }

    @PostMapping
    public void saveSensorData(@RequestBody SensorMessageRequest sensorMessageRequest) {
        log.info("new sensor message {}", sensorMessageRequest);
        sensorMessageService.saveSensorMessage(sensorMessageRequest);
    }
}
