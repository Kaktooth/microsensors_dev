package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.exception.NotFoundException;
import com.projects.microsensors.model.SensorMessageRequest;
import com.projects.microsensors.service.sensor.KeyRequestService;
import com.projects.microsensors.service.sensor.SensorMessageService;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<String> saveSensorData(@RequestBody SensorMessageRequest sensorMessageRequest) {
        var messageLength = sensorMessageRequest.message().length();
        if (messageLength > 96) {
            return ResponseEntity.unprocessableEntity().build();
        }

        try {
            var key = sensorMessageRequest.key();
            if (keyRequestService.isKeyUsedAllRequests(key)) {
                log.info("Key used all requests. Wait 1 minute to restore request count.");
                return ResponseEntity.noContent().build();
            } else {
                var requestKey = keyRequestService.findKey(key);
                var isUserSensor = sensorService.isUserSensor(sensorMessageRequest.sensorId(), requestKey.getId());
                if (isUserSensor) {
                    sensorMessageService.saveSensorMessage(sensorMessageRequest);
                    log.info("new sensor message {} length {}", sensorMessageRequest, messageLength);
                    return ResponseEntity.ok().build();
                } else {
                    throw new NotFoundException("sensor not found");
                }
            }
        } catch (Exception exception) {
            log.info("exception was encountered {}", exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
