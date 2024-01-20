package com.projects.microsensors.service.sensor;

import com.projects.microsensors.model.Authority;
import com.projects.microsensors.model.User;
import com.projects.microsensors.repository.AuthorityRepository;
import com.projects.microsensors.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final AuthorityRepository authorityRepository;

    private final UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public Authority registerAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
