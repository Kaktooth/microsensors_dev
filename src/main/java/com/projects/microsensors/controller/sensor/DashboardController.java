package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.SensorDTOService;
import com.projects.microsensors.service.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/update")
    public String update() {
        return "redirect:/dashboard";
    }
}
