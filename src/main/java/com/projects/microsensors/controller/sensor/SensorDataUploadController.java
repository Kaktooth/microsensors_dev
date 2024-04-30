package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorDataRequest;
import com.projects.microsensors.service.sensor.KeyRequestService;
import com.projects.microsensors.service.sensor.SensorDataService;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/sensor-data")
public class SensorDataUploadController {

    private final SensorDataService sensorDataService;
    private final SensorService sensorService;
    private final KeyRequestService keyRequestService;

    @GetMapping
    public String sensorDataPage() {
        return "Upload data...";
    }

    @PostMapping
    public ResponseEntity<String> saveSensorData(@RequestBody SensorDataRequest sensorDataRequest) {
        var dataSize = sensorDataRequest.data().length;
        if (dataSize > 96) {
            log.info("cant process, data size {}", dataSize);
            return ResponseEntity.unprocessableEntity().build();
        }

        try {
            var key = sensorDataRequest.key();
            if (keyRequestService.isKeyUsedAllRequests(key)) {
                log.info("Key used all requests. Wait 1 minute to restore request count.");
                return ResponseEntity.noContent().build();
            } else {
                var requestKey = keyRequestService.findKey(key);
                var isUserSensor = sensorService.isUserSensor(sensorDataRequest.sensorId(), requestKey.getId());
                if (isUserSensor) {
                    sensorDataService.saveSensorData(sensorDataRequest);
                    log.info("new sensor data {} size {}", sensorDataRequest, dataSize);
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
        } catch (Exception exception) {
            log.info("exception was encountered {}", exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
