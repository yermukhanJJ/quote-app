package com.kameleoon.trial.task.quote.controller;

import com.kameleoon.trial.task.quote.entity.Quote;
import com.kameleoon.trial.task.quote.entity.Users;
import com.kameleoon.trial.task.quote.payload.QuoteRequest;
import com.kameleoon.trial.task.quote.payload.QuoteResponse;
import com.kameleoon.trial.task.quote.service.QuoteService;
import com.kameleoon.trial.task.quote.service.ScoreService;
import com.kameleoon.trial.task.quote.service.UserService;
import com.kameleoon.trial.task.quote.service.specification.QuoteSpec;
import com.kameleoon.trial.task.quote.util.QuoteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Main controller for front part with Thymeleaf
 * @author Nurkali Yermukhan*
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/welcome")
public class MainController {

    private final QuoteService quoteService;
    private final UserService userService;
    private final QuoteValidator validator;
    private final ScoreService scoreService;

    /**
     * return home page
     * @param principal
     * @param model
     * @param word
     * @return <b>welcome.html</b>
     */
    @GetMapping
    public String welcome(Principal principal, Model model,
                          @RequestParam(value = "word", required = false) String word) {
        Users user = userService.getByUsername(principal.getName());
        Specification<Quote> filter = Specification.where(null);
        List<QuoteResponse> allQuotes = new ArrayList<>();
        if (word != null) {
            filter = filter.and(QuoteSpec.titleContains(word));
            allQuotes = quoteService.getAllByTitle(filter, PageRequest.of(0, 30));
        }
        Quote quote = new Quote();
        QuoteResponse result = new QuoteResponse();
        List<Quote> quotes = quoteService.getAllByUserId(user.getId());
        QuoteResponse random = quoteService.getRandomQuote();
        model.addAttribute("random", random);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("word", word);
        model.addAttribute("result", result);
        model.addAttribute("quotesbytitle", allQuotes);
        model.addAttribute("quote", quote);
        model.addAttribute("quotes", quotes);
        return "welcome";

    }

    /**
     * *
     * @param model
     * @return <b>Top 10 quotes</b>
     */
    @GetMapping("/top10")
    public String getTop10(Model model) {
        QuoteResponse quote = new QuoteResponse();
        List<QuoteResponse> quotes = quoteService.getTop10Quotes();
        model.addAttribute("quote", quote);
        model.addAttribute("quotes", quotes);
        return "/welcome/top10";
    }

    /**
     * *
     * @param model
     * @return <b>Flop 10 quotes</b>
     */
    @GetMapping("/flop10")
    public String getFlop10(Model model) {
        QuoteResponse quote = new QuoteResponse();
        List<QuoteResponse> quotes = quoteService.getFlop10Quotes();
        model.addAttribute("quote", quote);
        model.addAttribute("quotes", quotes);
        return "/welcome/flop10";
    }

    /**
     * *
     * @param request
     * @return <b>create.html</b>
     */
    @GetMapping("/create")
    public String addNewQuote(@ModelAttribute("quote") QuoteRequest request) {
        return "/welcome/create";
    }

    /**
     * Create new quote
     * @param request
     * @param result
     * @param principal
     * @return <b>welcome.html</b>
     */
    @PostMapping("/create")
    public String createQuote(@ModelAttribute(value = "quote") @Valid QuoteRequest request, BindingResult result, Principal principal) {
        validator.validate(request, result);

        if (result.hasErrors()) {
            System.out.println(result);
            return "/welcome/create";
        }

        quoteService.createNewQuote(principal, request);
        return "redirect:/welcome";
    }

    /**
     * Vote like
     * @param id_quote
     * @param principal
     * @return
     */
    @GetMapping("/like/{id}")
    public String like(@PathVariable(value = "id") Long id_quote, Principal principal) {
        scoreService.voteLike(id_quote, userService.getByUsername(principal.getName()).getId());
        return "redirect:/welcome";
    }

    /**
     * Vote dislike
     * @param id_quote
     * @param principal
     * @return
     */
    @GetMapping("/dislike/{id}")
    public String dislike(@PathVariable(value = "id") Long id_quote, Principal principal) {
        scoreService.voteDislike(id_quote, userService.getByUsername(principal.getName()).getId());
        return "redirect:/welcome";

    }

    /**
     * Get quote with id
     * @param model
     * @param id
     * @return <b>quote-page.html</b>
     */
    @GetMapping("/show/{id}")
    public String showOneQuote(Model model, @PathVariable(value = "id") Long id) {
        Quote quote = quoteService.getQuoteById(id);
        model.addAttribute("quote", quote);
        return "/welcome/quote-page";

    }

    /**
     *
     * @param model
     * @param id
     * @param principal
     * @return <b>edit.html<b/>
     */
    @PreAuthorize("@A.isAuthor( #principal, #id )")
    @GetMapping("/editor/{id}")
    public String editor(Model model, @PathVariable(value = "id") Long id, Principal principal) {
        Quote quote = quoteService.getQuoteById(id);
        model.addAttribute("quote", quote);
        return "/welcome/edit";

    }

    /**
     * Edit quote by id
     * @param request
     * @param result
     * @param id
     * @param principal
     * @return <b>welcome.html</b>
     */
    @PreAuthorize("@A.isAuthor( #principal, #id )")
    @PostMapping("/editor/{id}")
    public String edit(@ModelAttribute(value = "quote") @Valid QuoteRequest request
            , BindingResult result, @PathVariable(value = "id") Long id, Principal principal) {
        validator.validate(request, result);

        if (result.hasErrors()) {
            System.out.println(result);
            return "/welcome/edit";
        }
        quoteService.editQuote(request, id, principal);
        return "redirect:/welcome";

    }

    /**
     * Deleting quote by id
     * @param id
     * @param principal
     * @return <b>welcome.html</b>
     */
    @PreAuthorize("@A.isAuthor( #principal, #id )")
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable(value = "id") Long id, Principal principal) {
        quoteService.delQuote(id);
        return "redirect:/welcome";

    }
}
