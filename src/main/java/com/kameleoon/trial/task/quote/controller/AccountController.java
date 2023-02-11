package com.kameleoon.trial.task.quote.controller;

import com.kameleoon.trial.task.quote.payload.SignUpRequest;
import com.kameleoon.trial.task.quote.service.AccountService;
import com.kameleoon.trial.task.quote.util.AccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for authenticate and authorization, front part for Thymeleaf
 * @author Nurkali Yermukhan* *
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AccountController {

    private final AccountService registrationService;
    private final AccountValidator validator;

    /**
     * login page
     * @return <b>login.html</b>
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * sign-up
     * @param request
     * @return
     */
    @GetMapping("/sign-up")
    public String createAccountPage(@ModelAttribute("userRequest")SignUpRequest request) {
        return "auth/sign-up";
    }

    /**
     * sign-up
     * @param request
     * @param result
     * @return <b>login.html</b>
     */
    @PostMapping("/sign-up")
    public String createAccount(@ModelAttribute("userRequest") @Valid SignUpRequest request,
                                BindingResult result) {
        validator.validate(request,result);

        if (result.hasErrors()) {
            System.out.println(result);
            return "/auth/sign-up";
        }

        registrationService.registerUser(request);
        return "redirect:/auth/login";
    }
}
