package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorDTO;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.sensor.SensorDTOService;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getDashboard(Model model,
                               @RequestParam(value = "id", required = false)
                                   String id,
                               @ModelAttribute SensorDTO selectedSensor) {
        if (selectedSensor.getId() != null) {
            log.info("attach sensor");
            SensorDTO sensorDTO = sensorDTOService.getSensorDTO(selectedSensor.getId());
            model.addAttribute("sensor", sensorDTO);
        }
        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        model.addAttribute("id", id);
        List<Sensor> sensorList = sensorService.getAllSensors();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());

        return "dashboard";
    }

    @GetMapping("/{id}")
    public String getDashboard(Model model,
                               @PathVariable("id") String id) {
        if (id != null) {
            log.info("attach sensor");
            SensorDTO sensorDTO = sensorDTOService.getSensorDTO(UUID.fromString(id));
            model.addAttribute("sensor", sensorDTO);
        }
        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        List<Sensor> sensorList = sensorService.getAllSensors();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());

        return "dashboard";
    }

    @PostMapping
    public String createNewSensor(@ModelAttribute SensorRequest sensorRequest,
                                  Model model) {
        log.info("new sensor {}", sensorRequest);
        Sensor sensor = sensorService.saveSensor(sensorRequest);
        if (sensor.getId() != null) {
            log.info("attach sensor");
            SensorDTO sensorDTO = sensorDTOService.getSensorDTO(sensor.getId());
            model.addAttribute("sensor", sensorDTO);
        }
        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        model.addAttribute("id", sensor.getId().toString());
        List<Sensor> sensorList = sensorService.getAllSensors();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());
        return "dashboard";
    }
}
