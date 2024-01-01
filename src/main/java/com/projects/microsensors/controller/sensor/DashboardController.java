package com.projects.microsensors.controller.sensor;

import com.opencsv.CSVWriter;
import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorDTO;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.sensor.SensorDTOService;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final SensorService sensorService;
    private final SensorDTOService sensorDTOService;
    public final static String TEXT_CSV = "text/csv";
    public final static MediaType TEXT_CSV_TYPE = new MediaType("text", "csv");

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
        model.addAttribute("chart", false);
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
        log.info("attach sensor");
        SensorDTO sensorDTO = sensorDTOService.getSensorDTO(UUID.fromString(id));
        model.addAttribute("sensor", sensorDTO);
        model.addAttribute("chart", false);
        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        List<Sensor> sensorList = sensorService.getAllSensors();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());

        return "dashboard";
    }

    @GetMapping("/{id}/chart")
    public String getChart(Model model,
                           @PathVariable("id") String id) {

        log.info("attach sensor");
        SensorDTO sensorDTO = sensorDTOService.getSensorDTO(UUID.fromString(id));
        model.addAttribute("sensor", sensorDTO);
        model.addAttribute("chart", true);

        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        List<Sensor> sensorList = sensorService.getAllSensors();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());

        return "dashboard";
    }

    @GetMapping("/{id}/chart-data")
    @ResponseBody
    public ResponseEntity<String> getChartData(Model model,
                                               @PathVariable("id") String id) {

        SensorDTO sensorDTO = sensorDTOService.getSensorDTO(UUID.fromString(id));
        HttpHeaders headers = new HttpHeaders();
        String csv = null;
        try (StringWriter sw = new StringWriter(); CSVWriter csvWriter = new CSVWriter(sw)) {
            var sensorData = sensorDTO.getSensorData();
            var firstDataLength = new String(sensorData.iterator().next().getData()).split(" ").length;
            String[] labels = new String[firstDataLength + 1];
            labels[0] = "Date";
            for (int i = 1; i < labels.length; i++) {
                labels[i] = "Sensor data " + i;
            }

            csvWriter.writeNext(labels);
            for (var data : sensorData) {
                var writeData = new StringBuilder(new String(data.getData()).replaceAll("[^\\d\\s.,]", ""));
                writeData.insert(0, data.getReceiveDate().toLocalDateTime().toString() + " ");
                var numericalSensorData = writeData.toString().split(" ");
                csvWriter.writeNext(numericalSensorData);
            }
            csv = sw.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return ResponseEntity.ok().headers(headers).body(csv);
    }

    @PostMapping
    public String createNewSensor(@ModelAttribute SensorRequest sensorRequest,
                                  Model model) {
        log.info("new sensor {}", sensorRequest);
        Sensor sensor = sensorService.saveSensor(sensorRequest);
        log.info("attach sensor");
        SensorDTO sensorDTO = sensorDTOService.getSensorDTO(sensor.getId());
        model.addAttribute("sensor", sensorDTO);

        model.addAttribute("chart", false);
        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        model.addAttribute("id", sensor.getId().toString());
        List<Sensor> sensorList = sensorService.getAllSensors();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());
        return "dashboard";
    }
}
