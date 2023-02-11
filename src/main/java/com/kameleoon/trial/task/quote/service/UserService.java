package com.kameleoon.trial.task.quote.service;

import com.kameleoon.trial.task.quote.entity.Users;
import com.kameleoon.trial.task.quote.payload.Profile;
import com.kameleoon.trial.task.quote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Users getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + "not found!"));

    }

    @Transactional(readOnly = true)
    public Users getByUsername(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + name + " not found!"));
    }

    @Transactional(readOnly = true)
    public Profile getProfile(String name) {
        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + name + " not found!"));
        log.info("Getting profile with username: " + name);
        return new Profile(user.getUsername(), user.getEmail(), user.getCreateAt());

    }
}
