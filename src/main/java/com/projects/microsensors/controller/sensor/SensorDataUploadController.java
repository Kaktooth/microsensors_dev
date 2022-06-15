package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorDataRequest;
import com.projects.microsensors.model.UpdateEvent;
import com.projects.microsensors.service.SensorDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.core.event.AfterCreateEvent;
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
    private final ApplicationEventPublisher publisher;

    @GetMapping
    public String sensorDataPage() {
        return "Upload data...";
    }

    @PostMapping
    public void saveSensorData(@RequestBody SensorDataRequest sensorDataRequest) {
        log.info("new sensor data {}", sensorDataRequest);
        sensorDataService.saveSensorData(sensorDataRequest);
        publisher.publishEvent(new UpdateEvent(sensorDataRequest));
    }
}
