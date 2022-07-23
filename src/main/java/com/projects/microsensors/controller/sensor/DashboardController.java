package com.projects.microsensors.controller.sensor;

import com.opencsv.CSVWriter;
import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorDTO;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.sensor.SensorDTOService;
import com.projects.microsensors.service.sensor.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.BinaryStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Arrays;
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
    public ResponseEntity<Object> getChartData(Model model,
                                           @PathVariable("id") String id) {

        SensorDTO sensorDTO = sensorDTOService.getSensorDTO(UUID.fromString(id));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.csv");
        String csv = null;
        try (StringWriter sw = new StringWriter(); CSVWriter csvWriter = new CSVWriter(sw)) {
            csvWriter.writeNext(new String[]{"Data", "Date"});
            for (var data : sensorDTO.getSensorData()) {
                csvWriter.writeNext(new String[]{Arrays.toString(data.getData()),
                    data.getReceiveDate().toString()});
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
