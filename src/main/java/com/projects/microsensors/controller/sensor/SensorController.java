package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/sensors")
public class SensorController {

    private final SensorService sensorService;

    @GetMapping
    public String sensorPage() {
        return "upload sensors... ";
    }

    @PostMapping
    public void saveSensor(@RequestBody SensorRequest sensorRequest) {
        log.info("new sensor {}", sensorRequest);
        sensorService.saveSensor(sensorRequest);
    }
}
