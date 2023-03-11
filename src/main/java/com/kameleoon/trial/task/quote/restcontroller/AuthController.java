package com.kameleoon.trial.task.quote.restcontroller;

import com.kameleoon.trial.task.quote.payload.LoginRequest;
import com.kameleoon.trial.task.quote.payload.SignUpRequest;
import com.kameleoon.trial.task.quote.service.AccountService;
import com.kameleoon.trial.task.quote.util.AccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private final AccountValidator validator;

    /**
     * Return jwt token after success authorization
     * @param request
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link JwtResponse}
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request, BindingResult result) {
        return ResponseEntity.ok(registrationService.authenticate(request));

    }

    /**
     * Registration user
     * @param request
     */
    @PostMapping("/sign-up")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody SignUpRequest request, BindingResult result) throws Exception {
        validator.validate(request, result);
        if (result.hasErrors())
            throw new Exception("Bad request 400: " + result);

        registrationService.registerUser(request);
    }

}
