package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.exception.NotFoundException;
import com.projects.microsensors.model.SensorMessageRequest;
import com.projects.microsensors.service.sensor.KeyRequestService;
import com.projects.microsensors.service.sensor.SensorMessageService;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/sensor-messages")
public class SensorMessageController {

    private final SensorMessageService sensorMessageService;
    private final SensorService sensorService;
    private final KeyRequestService keyRequestService;

    @GetMapping
    public String sensorDataPage() {
        return "Upload message...";
    }

    @PostMapping("/{key}")
    public void saveSensorData(@PathVariable("key") UUID key, @RequestBody SensorMessageRequest sensorMessageRequest) {
        try {
            if (keyRequestService.isKeyUsedAllRequests(key)) {
                log.info("Key used all requests. Wait 1 minute to restore request count.");
            } else {
                var requestKey = keyRequestService.findKey(key);
                var isUserSensor = sensorService.isUserSensor(sensorMessageRequest.sensorId(), requestKey.getId());
                if (isUserSensor) {
                    sensorMessageService.saveSensorMessage(sensorMessageRequest);
                    log.info("new sensor message {}", sensorMessageRequest);
                } else {
                    throw new NotFoundException("sensor not found");
                }
            }
        } catch (Exception exception) {
            log.info("exception was encountered {}", exception.getMessage());
        }
    }
}
