package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.*;
import com.projects.microsensors.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.projects.microsensors.common.AppConstraints.Sensors.DATA_LIMIT;
import static com.projects.microsensors.common.AppConstraints.Sensors.MESSAGES_LIMIT;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;
    private final SensorDataRepository sensorDataRepository;
    private final SensorMessageRepository sensorMessageRepository;
    private final UserRepository userRepository;
    private final KeyRepository keyRepository;

    public Sensor save(SensorRequest sensorRequest) {
        UUID id = sensorRequest.getId();
        if (id == null) {
            id = UUID.randomUUID();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = userRepository.findUserByUsername(authentication.getName()).getId();

        Sensor sensor = Sensor.builder()
                .id(id)
                .sensorTypes(sensorRequest.getSensorTypesConverted())
                .placements(sensorRequest.getPlacementsConverted())
                .country(sensorRequest.getCountry())
                .personal(sensorRequest.getPersonal())
                .userId(userId)
                .build();

        return sensorRepository.save(sensor);
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    public List<Sensor> getPublic() {
        return sensorRepository.findAllByPersonal(false);
    }

    public List<Sensor> getForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = userRepository.findUserByUsername(authentication.getName()).getId();
        return sensorRepository.findAllByUserId(userId);
    }

    public SensorDTO getDTO(UUID id) {
        Sensor sensor = sensorRepository.getReferenceById(id);
        List<SensorData> sensorData = sensorDataRepository.findAllBySensorId(id);
        List<SensorMessage> sensorMessages = sensorMessageRepository.findAllBySensorId(id);

        sensorData = sensorData.stream().sorted()
                .skip(Math.max(0, sensorData.size() - MESSAGES_LIMIT))
                .limit(DATA_LIMIT).toList();

        sensorMessages = sensorMessages.stream().sorted()
                .skip(Math.max(0, sensorMessages.size() - MESSAGES_LIMIT))
                .limit(MESSAGES_LIMIT).toList();

        return SensorDTO.builder()
                .id(sensor.getId())
                .country(sensor.getCountry())
                .sensorTypes(sensor.getSensorTypes())
                .placements(sensor.getPlacements())
                .personal(sensor.getPersonal())
                .sensorData(sensorData)
                .sensorMessages(sensorMessages)
                .userId(sensor.getUserId())
                .points(sensorData.size() + sensorMessages.size())
                .build();
    }

    public boolean isUserSensor(UUID sensorId, UUID keyId) {
        var sensor = sensorRepository.findById(sensorId).get();
        var keyUserId = keyRepository.findById(keyId).get().getUserId();
        return sensor.getUserId().equals(keyUserId);
    }

    public void delete(UUID sensorId) {
        sensorRepository.deleteById(sensorId);
    }

    public void deleteData(UUID sensorId) {
        sensorDataRepository.deleteAllBySensorId(sensorId);
        sensorMessageRepository.deleteAllBySensorId(sensorId);
    }
}
