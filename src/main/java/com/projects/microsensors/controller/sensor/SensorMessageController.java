package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorMessageRequest;
import com.projects.microsensors.service.sensor.SensorMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/sensor-messages")
public class SensorMessageController {

    private final SensorMessageService sensorMessageService;

    @GetMapping
    public String sensorDataPage() {
        return "Upload message...";
    }

    @PostMapping
    public void saveSensorData(@RequestBody SensorMessageRequest sensorMessageRequest) throws IOException {
        try {
            sensorMessageService.saveSensorMessage(sensorMessageRequest);
            log.info("new sensor message {}", sensorMessageRequest);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
