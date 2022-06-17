package com.projects.microsensors.controller.sensor;

import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorDTO;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.sensor.SensorDTOService;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private SensorDTO sensorDTO;

    @GetMapping("/update")
    public String update() {
        log.info("update");
        return "redirect:/dashboard";
    }

    @GetMapping
    public String getDashboard(Model model, @RequestParam(value = "sensorId", required = false) String sensorId) {
        if (sensorId != null) {
            ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
            threadPoolTaskScheduler.setPoolSize(5);
            threadPoolTaskScheduler.initialize();

            sensorDTO = sensorDTOService.getSensorDTO(UUID.fromString(sensorId));
            threadPoolTaskScheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    SensorDTO refreshedDto = sensorDTOService.getSensorDTO(UUID.fromString(sensorId));
                    sensorDTO = SensorDTO.builder()
                        .id(refreshedDto.getId())
                        .name(refreshedDto.getName())
                        .sensorInfo(refreshedDto.getSensorInfo())
                        .sensorMessages(refreshedDto.getSensorMessages())
                        .sensorData(refreshedDto.getSensorData())
                        .build();
                }
            }, 2000);
            model.addAttribute("sensor", sensorDTO);
        }
        log.info("loading dashboard");
        model.addAttribute("sensorId", sensorId);
        List<Sensor> sensorList = sensorService.getAllSensors();
        model.addAttribute("sensorList", sensorList);
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
