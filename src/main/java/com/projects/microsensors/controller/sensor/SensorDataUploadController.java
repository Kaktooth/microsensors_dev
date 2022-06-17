package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorDataRequest;
import com.projects.microsensors.service.sensor.SensorDataService;
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
@RequestMapping("api/v1/sensor-data")
public class SensorDataUploadController {

    private final SensorDataService sensorDataService;

    @GetMapping
    public String sensorDataPage() {
        return "Upload data...";
    }

    @PostMapping
    public void saveSensorData(@RequestBody SensorDataRequest sensorDataRequest) {
        try {
            sensorDataService.saveSensorData(sensorDataRequest);
            log.info("new sensor data {}", sensorDataRequest);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
