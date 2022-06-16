package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.sensor.SensorDTOService;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getDashboard(RedirectAttributes redirectAttributes,
                               @RequestParam(value = "sensorId", required = false)
                                   String sensorId) {
        if (sensorId != null) {
            redirectAttributes.addAttribute("sensor",
                sensorDTOService.getSensorDTO(UUID.fromString(sensorId)));
        }
        log.info("loading dashboard");
        redirectAttributes.addFlashAttribute("sensorId", sensorId);
        List<Sensor> sensorList = sensorService.getAllSensors();
        redirectAttributes.addAttribute("sensorList", sensorList);
        sensorList.add(new Sensor(UUID.randomUUID(), "new sensor", "info"));

        redirectAttributes.addAttribute("sensorRequest", new SensorRequest());

        return "dashboard";
    }

    @PostMapping
    public String createNewSensor(@ModelAttribute SensorRequest sensorRequest) {
        log.info("new sensor {}", sensorRequest);
        sensorService.saveSensor(sensorRequest);
        return "dashboard";
    }
}
