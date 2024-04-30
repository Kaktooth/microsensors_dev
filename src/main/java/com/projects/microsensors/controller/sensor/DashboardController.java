package com.projects.microsensors.controller.sensor;

import com.opencsv.CSVWriter;
import com.projects.microsensors.common.AppConstraints;
import com.projects.microsensors.common.Pagination;
import com.projects.microsensors.model.Key;
import com.projects.microsensors.model.Sensor;
import com.projects.microsensors.model.SensorDTO;
import com.projects.microsensors.model.SensorRequest;
import com.projects.microsensors.service.sensor.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import static com.projects.microsensors.common.AppConstraints.FormattedCode.CERT;
import static com.projects.microsensors.common.AppConstraints.FormattedCode.DHT22_Arduino;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final SensorService sensorService;
    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final KeyRequestService keyRequestService;
    private final SensorTypesService sensorTypesService;
    private final SensorLocationService sensorLocationService;
    public static final String TEXT_CSV = "text/csv";
    public static final MediaType TEXT_CSV_TYPE = new MediaType("text", "csv");

    @GetMapping
    public String getDashboard(@ModelAttribute SensorDTO selectedSensor, Model model) {
        model.addAttribute("sensors_dashboard", true);
        if (selectedSensor.getId() != null) {
            log.info("attach sensor");
            SensorDTO sensorDTO = sensorService.getDTO(selectedSensor.getId());
            model.addAttribute("sensor", sensorDTO);
        }
        model.addAttribute("chart", false);
        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        model.addAttribute("sensors_dashboard", true);
        initializeSensorAttributes(model);
        return "dashboard";
    }

    private void initializeSensorAttributes(Model model) {
        var sensorTypes = sensorTypesService.getSensorTypes();
        var placements = sensorTypesService.getPlacementTypes();
        var countries = sensorLocationService.getCountries();
        model.addAttribute("sensorRequest", new SensorRequest());
        model.addAttribute("sensorTypesList", sensorTypes);
        model.addAttribute("placementsList", placements);
        model.addAttribute("countries", countries);
        model.addAttribute("personal", false);
    }

    @GetMapping("/sensor/{id}")
    public String getDashboard(Model model,
                               @PathVariable("id") UUID id,
                               @CurrentSecurityContext(expression = "authentication") Authentication authentication) {

        var isAuthorized = authorizationService.authorizeUserForPrivateSensor(id, authentication);
        if (!isAuthorized) {
            return "redirect:/dashboard";
        }
        log.info("attach sensor");
        SensorDTO sensorDTO = sensorService.getDTO(id);
        model.addAttribute("sensor", sensorDTO);
        model.addAttribute("chart", false);
        model.addAttribute("selectedSensor", new SensorDTO());
        model.addAttribute("userIsCreator", authorizationService.authorizeUserForAllSensors(id, authentication));
        log.info("loading dashboard");

        List<Sensor> sensorList = sensorService.getAll();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());

        return "dashboard";
    }

    @DeleteMapping("/sensor/{id}")
    public String deleteSensor(@PathVariable("id") UUID id, @CurrentSecurityContext(expression = "authentication") Authentication authentication) {

        var isAuthorized = authorizationService.authorizeUserForAllSensors(id, authentication);
        if (!isAuthorized) {
            return "redirect:/dashboard";
        }
        sensorService.delete(id);

        return "redirect:/dashboard";
    }

    @DeleteMapping("/sensor/{id}/data")
    public String deleteSensorData(@PathVariable("id") UUID id, @CurrentSecurityContext(expression = "authentication") Authentication authentication) {

        var isAuthorized = authorizationService.authorizeUserForAllSensors(id, authentication);
        if (!isAuthorized) {
            return "redirect:/dashboard";
        }
        sensorService.deleteData(id);

        return "redirect:/dashboard/sensor/" + id;
    }

    @GetMapping("/sensors/{currentPage}")
    public String getPublicSensors(Model model, @PathVariable("currentPage") Integer currentPage) {

        var sensors = sensorService.getPublic();
        model.addAttribute("sensorsData", true);
        paginateContent(sensors, currentPage, model);

        return "dashboard";
    }

    @GetMapping("/user-sensors/{currentPage}")
    public String getUserSensors(Model model, @PathVariable("currentPage") Integer currentPage) {

        var userSensors = sensorService.getForCurrentUser();
        model.addAttribute("userSensorsData", true);
        paginateContent(userSensors, currentPage, model);
        initializeSensorAttributes(model);
        return "dashboard";
    }

    private void paginateContent(List<Sensor> sensors, Integer currentPage, Model model) {
        if (currentPage == null) {
            currentPage = 0;
        }

        var pagination = new Pagination<>(sensors);
        var size = pagination.getPageCount();
        var paginatedSensors = pagination.toMap(AppConstraints.Pagination.PAGE_SIZE, size);
        model.addAttribute("sensors", paginatedSensors.get(currentPage));
        model.addAttribute("sensorsKeys", paginatedSensors.keySet());
        model.addAttribute("pageCount", size);
        model.addAttribute("pagCount", 1);
    }

    @GetMapping("/sensor/{id}/chart")
    public String getChart(Model model, @PathVariable("id") UUID id,
                           @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        var isAuthorized = authorizationService.authorizeUserForPrivateSensor(id, authentication);
        if (!isAuthorized) {
            return "redirect:/dashboard";
        }

        log.info("attach sensor");
        SensorDTO sensorDTO = sensorService.getDTO(id);
        model.addAttribute("sensor", sensorDTO);
        model.addAttribute("chart", true);

        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        List<Sensor> sensorList = sensorService.getAll();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());

        model.addAttribute("id", id);

        return "dashboard";
    }

    @GetMapping("/sensor/{id}/csv/{key}")
    @ResponseBody
    public ResponseEntity<String> getCSVData(Model model,
                                             @PathVariable("id") UUID id,
                                             @PathVariable("key") UUID key,
                                             @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        var isAuthorized = authorizationService.authorizeUserForPrivateSensor(id, authentication);

        getCreateKeyPage(model, authentication);

        if (!isAuthorized || keyRequestService.isKeyUsedAllRequests(key)) {
            return ResponseEntity.noContent().build();
        }

        SensorDTO sensorDTO = sensorService.getDTO(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sensorData.csv");
        String csv = null;
        try (StringWriter sw = new StringWriter(); CSVWriter csvWriter = new CSVWriter(sw)) {
            var sensorData = sensorDTO.getSensorData();

            if (!sensorData.iterator().hasNext()) {
                return ResponseEntity.ok().headers(headers).body("");
            }

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

    @GetMapping("/sensor/{id}/json/{key}")
    @ResponseBody
    public ResponseEntity<SensorDTO> getSensorData(@PathVariable("id") UUID id,
                                                   @PathVariable("key") UUID key,
                                                   @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        var isAuthorized = authorizationService.authorizeUserForPrivateSensor(id, authentication);

        if (!isAuthorized || keyRequestService.isKeyUsedAllRequests(key)) {
            return ResponseEntity.noContent().build();
        }

        SensorDTO sensorDTO = sensorService.getDTO(id);
        return ResponseEntity.ok().body(sensorDTO);
    }

    @PostMapping
    public String createNewSensor(@ModelAttribute SensorRequest sensorRequest,
                                  Model model) {
        log.info("new sensor {}", sensorRequest);
        var sensorTypes = sensorTypesService.getAllSensorTypes(sensorRequest.getSensorTypes());
        var placements = sensorTypesService.getAllPlacementTypes(sensorRequest.getPlacements());
        sensorRequest.setSensorTypesConverted(sensorTypes);
        sensorRequest.setPlacementsConverted(placements);
        Sensor sensor = sensorService.save(sensorRequest);
        log.info("attach sensor");
        SensorDTO sensorDTO = sensorService.getDTO(sensor.getId());
        model.addAttribute("sensor", sensorDTO);

        model.addAttribute("chart", false);
        model.addAttribute("selectedSensor", new SensorDTO());
        log.info("loading dashboard");

        model.addAttribute("id", sensor.getId().toString());
        List<Sensor> sensorList = sensorService.getAll();
        model.addAttribute("sensorList", sensorList);
        model.addAttribute("sensorRequest", new SensorRequest());
        return "redirect:/dashboard/sensor/" + sensor.getId();
    }

    @PostMapping("/sensor/{id}")
    public String updateSensor(@PathVariable("id") UUID id, @ModelAttribute SensorRequest sensorRequest,
                               @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                               Model model) {

        var isAuthorized = authorizationService.authorizeUserForPrivateSensor(id, authentication);
        if (!isAuthorized) {
            return "redirect:/dashboard";
        }
        var sensorTypes = sensorTypesService.getAllSensorTypes(sensorRequest.getSensorTypes());
        var placements = sensorTypesService.getAllPlacementTypes(sensorRequest.getPlacements());
        sensorRequest.setSensorTypesConverted(sensorTypes);
        sensorRequest.setPlacementsConverted(placements);
        sensorService.save(sensorRequest);
        model.addAttribute("id", id);

        return "redirect:/dashboard/sensor/" + id;
    }

    @GetMapping("/register-key")
    public String getCreateKeyPage(Model model, @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        model.addAttribute("create_key", true);
        var key = getKey(authentication);
        model.addAttribute("keyBody", key);
        model.addAttribute("enableList", false);
        return "dashboard";
    }

    @GetMapping("/request-key")
    @ResponseBody
    public ResponseEntity<UUID> getRequestKey(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {

        return ResponseEntity.ok().body(getKey(authentication).getKey());
    }

    private Key getKey(Authentication authentication) {
        var authDetails = (WebAuthenticationDetails) authentication.getDetails();
        var ip = authDetails.getRemoteAddress();
        var user = userService.findUserByUsername(authentication.getName());
        var key = keyRequestService.findKeyByUserId(user.getId());
        var keyByIp = keyRequestService.findKey(ip);
        if (key == null && keyByIp == null) {
            key = keyRequestService.registerKey(ip, user);
        } else if (keyByIp != null) {
            return keyByIp;
        }
        return key;
    }

    @GetMapping("/about")
    public String getAboutPage(Model model) {
        model.addAttribute("about", true);

        return "dashboard";
    }

    @GetMapping("/tutorial")
    public String getGuidePage(Model model) {
        model.addAttribute("tutorial", true);
        model.addAttribute("tutorialCode", DHT22_Arduino);
        model.addAttribute("cert", CERT);

        return "dashboard";
    }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }
}
