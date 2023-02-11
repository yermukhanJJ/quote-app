package com.kameleoon.trial.task.quote.restcontroller;

import com.kameleoon.trial.task.quote.payload.LoginRequest;
import com.kameleoon.trial.task.quote.payload.SignUpRequest;
import com.kameleoon.trial.task.quote.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kameleoon.trial.task.quote.payload.JwtResponse;

import javax.validation.Valid;

/**
 * Rest controller for sign-in and sign-up
 * @author Nurkali Yermukhan* *
 */
@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService registrationService;

    /**
     * Return jwt token after success authorization
     * @param request
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link JwtResponse}
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(registrationService.authenticate(request));

    }

    /**
     * Registration user
     * @param request
     */
    @PostMapping("/sign-up")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser(@RequestBody SignUpRequest request) {
        registrationService.registerUser(request);
    }

}
