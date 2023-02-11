package com.kameleoon.trial.task.quote.controller;

import com.kameleoon.trial.task.quote.entity.Quote;
import com.kameleoon.trial.task.quote.payload.Profile;
import com.kameleoon.trial.task.quote.service.QuoteService;
import com.kameleoon.trial.task.quote.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Thymeleaf controller for working with profile
 * @author Nurkali Yermukhan* *
 */
@Controller
@RequestMapping(value = "/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final QuoteService quoteService;

    /**
     * Getting info the user by username
     * @param model
     * @param username
     * @return <b>Page</b>: profile.html
     */
    @GetMapping("/{username}")
    public String profile(Model model, @PathVariable(value = "username") String username) {
        Profile profile = userService.getProfile(username);
        List<Quote> quotes = quoteService.getAllByUserId(userService.getByUsername(username).getId());
        Quote quote = new Quote();
        model.addAttribute("quote",quote);
        model.addAttribute("profile",profile);
        model.addAttribute("quotes",quotes);
        return "/profile";
    }
}
