package com.kameleoon.trial.task.quote.service;

import com.kameleoon.trial.task.quote.entity.Role;
import com.kameleoon.trial.task.quote.entity.Users;
import com.kameleoon.trial.task.quote.payload.JwtResponse;
import com.kameleoon.trial.task.quote.payload.LoginRequest;
import com.kameleoon.trial.task.quote.payload.SignUpRequest;
import com.kameleoon.trial.task.quote.repository.RoleRepository;
import com.kameleoon.trial.task.quote.repository.UserRepository;
import com.kameleoon.trial.task.quote.security.UserDetailsImpl;
import com.kameleoon.trial.task.quote.security.jwt.jwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for authorization *
 * @author Nurkali Yermukhan*
 */
@Service
@RequiredArgsConstructor
@Transient
@Slf4j
public class AccountService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final jwtUtils jwtUtil;

    public void registerUser(SignUpRequest request) {
        //Create new account
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        Users user = new Users(request.getUsername(), request.getEmail(), encoder.encode(request.getPassword()), LocalDateTime.now());
        user.setRoles(roles);

        userRepository.save(user);
        log.info("Create new account with role: " + userRole.getName());
    }

    public JwtResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

    }
}
