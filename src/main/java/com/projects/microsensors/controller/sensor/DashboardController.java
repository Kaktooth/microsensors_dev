package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorMessage;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.SensorDTOService;
import com.projects.microsensors.service.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final SensorService sensorService;
    private final SensorDTOService sensorDTOService;

    @GetMapping
    public String getDashboard(Model model, @RequestParam(value = "sensorId", required = false) String sensorId) {
        if (sensorId != null) {
            model.addAttribute("sensor",
                sensorDTOService.getSensorDTO(UUID.fromString(sensorId)));
        }
        model.addAttribute("sensorList", sensorService.getAllSensors());
        model.addAttribute("sensorRequest", new SensorRequest());

        return "dashboard";
    }

    @PostMapping
    public String createNewSensor(@ModelAttribute SensorRequest sensorRequest) {
        log.info("new sensor {}", sensorRequest);
        sensorService.saveSensor(sensorRequest);
        return "dashboard";
    }
}
