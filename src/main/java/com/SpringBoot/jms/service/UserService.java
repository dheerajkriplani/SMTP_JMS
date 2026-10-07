package com.SpringBoot.jms.service;


import com.SpringBoot.jms.entity.User;
import com.SpringBoot.jms.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        log.info("Creating user {}", user);
        return userRepository.save(user);
    }
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
