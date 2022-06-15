package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorMessageRequest;
import com.projects.microsensors.service.SensorMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("api/v1/sensor-messages")
public class SensorMessageController {

    private final SensorMessageService sensorMessageService;

    private final List<SseEmitter> sseEmitter = new LinkedList<>();

    @GetMapping ("/register")
    public SseEmitter register() throws IOException {
        log.info("Registering a stream.");

        SseEmitter emitter = new SseEmitter();

        synchronized (sseEmitter) {
            sseEmitter.add(emitter);
        }
        emitter.onCompletion(() -> sseEmitter.remove(emitter));

        return emitter;
    }

    @GetMapping
    public String sensorDataPage() {
        return "Upload message...";
    }

    @PostMapping
    public void saveSensorData(@RequestBody SensorMessageRequest sensorMessageRequest) {
        sensorMessageService.saveSensorMessage(sensorMessageRequest);
        log.info("new sensor message {}", sensorMessageRequest);
        synchronized (sseEmitter) {
            sseEmitter.forEach((SseEmitter emitter) -> {
                try {
                    emitter.send(true, MediaType.APPLICATION_JSON);
                } catch (IOException e) {
                    emitter.complete();
                    sseEmitter.remove(emitter);
                }
            });
        }
    }
}
