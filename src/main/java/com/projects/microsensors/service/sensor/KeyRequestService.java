package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.Key;
import com.projects.microsensors.model.KeyRequest;
import com.projects.microsensors.model.User;
import com.projects.microsensors.repository.KeyRepository;
import com.projects.microsensors.repository.KeyRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class KeyRequestService {

    private final KeyRepository keyRepository;

    private final KeyRequestRepository keyRequestRepository;

    public Key registerKey(String ip, User user) {
        var key = Key.builder()
                .id(UUID.randomUUID())
                .creationDate(Timestamp.from(Instant.now()))
                .key(UUID.randomUUID())
                .userId(user.getId())
                .ip(ip).build();
        keyRepository.save(key);
        return key;
    }

    public Key findKey(String ip) {
        return keyRepository.findByIp(ip);
    }

    public Key findKey(UUID key) {
        return keyRepository.findByKey(key);
    }

    public Key findKeyByUserId(UUID userId) {
        return keyRepository.findByUserId(userId);
    }

    public boolean isKeyUsedAllRequests(UUID key) {

        var time = LocalDateTime.now();
        var startRequestUsageDate = Timestamp.valueOf(time.withSecond(0));
        var endRequestUsageDate = Timestamp.valueOf(time.withSecond(59));

        var userKey = keyRepository.findByKey(key);
        registerRequest(userKey);

        var keyRequests = keyRequestRepository.findAllByIdAndRequestDateBetween(userKey.getId(), startRequestUsageDate, endRequestUsageDate);
        return keyRequests.size() > 30;
    }

    private KeyRequest registerRequest(Key key) {
        var keyRequest = KeyRequest.builder()
                .id(UUID.randomUUID())
                .requestDate(Timestamp.from(Instant.now()))
                .keyId(key.getId()).build();
        return keyRequestRepository.save(keyRequest);
    }
}
