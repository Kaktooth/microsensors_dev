package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorDataRequest;
import com.projects.microsensors.service.SensorDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
        sensorDataService.saveSensorData(sensorDataRequest);
        log.info("new sensor data {}", sensorDataRequest);

    }
}
