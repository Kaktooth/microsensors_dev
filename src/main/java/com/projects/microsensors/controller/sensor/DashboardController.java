package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/dashboard")
public record DashboardController(SensorService sensorService) {

    @GetMapping
    public String getDashboard(Model model) {
        model.addAttribute("sensorRequest", new SensorRequest());
        return "dashboard";
    }

    @PostMapping
    public String createNewSensor(@RequestBody SensorRequest sensorRequest) {
        log.info("new sensor {}", sensorRequest);
        sensorService.saveSensor(sensorRequest);
        return "dashboard";
    }
}
