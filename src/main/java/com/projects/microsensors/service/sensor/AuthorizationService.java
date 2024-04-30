package com.projects.microsensors.service.sensor;

import com.projects.microsensors.repository.SensorRepository;
import com.projects.microsensors.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AuthorizationService {
    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;

    public boolean authorizeUserForPrivateSensor(UUID sensorId, Authentication authentication) {

        var sensor = sensorRepository.findById(sensorId).get();
        return !sensor.getPersonal() || authorizeUserForAllSensors(sensorId, authentication);
    }

    public boolean authorizeUserForAllSensors(UUID sensorId, Authentication authentication) {

        var sensor = sensorRepository.findById(sensorId).get();
        var user = userRepository.findUserByUsername(authentication.getName());
        return sensor.getUserId().equals(user.getId());
    }
}
