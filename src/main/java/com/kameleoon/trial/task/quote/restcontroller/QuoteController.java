package com.kameleoon.trial.task.quote.restcontroller;

import com.kameleoon.trial.task.quote.payload.QuoteRequest;
import com.kameleoon.trial.task.quote.service.QuoteService;
import com.kameleoon.trial.task.quote.service.ScoreService;
import com.kameleoon.trial.task.quote.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.kameleoon.trial.task.quote.entity.Quote;
import com.kameleoon.trial.task.quote.payload.QuoteResponse;
import com.kameleoon.trial.task.quote.payload.Profile;
import java.util.List;
import java.security.Principal;

/**
 * Rest controller for working with quote
 * @author Nurkali Yermukhan
 */
@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;
    private final UserService userService;
    private final ScoreService scoreService;

    /**
     * *
     * @param id
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link Quote} object
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getQuoteById (@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(quoteService.getQuoteById(id));

    }

    /**
     * *
     * @param principal
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link List<Quote>}
     */
    @GetMapping(value = "/myquotes")
    public ResponseEntity<?> getMyQuote(Principal principal) {
        return ResponseEntity.ok(quoteService.getAllByUserId(userService.getByUsername(principal.getName()).getId()));

    }

    /**
     * *
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link List<QuoteResponse>}
     */
    @GetMapping(value = "/top10")
    public ResponseEntity<?> getTopQuote() {
        return ResponseEntity.ok(quoteService.getTop10Quotes());

    }

    /**
     * *
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link List<QuoteResponse>}
     */
    @GetMapping(value = "/flop10")
    public ResponseEntity<?> getFlopQuote() {
        return ResponseEntity.ok(quoteService.getFlop10Quotes());

    }

    /**
     * *
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link QuoteResponse}
     */
    @GetMapping(value = "/random")
    public ResponseEntity<?> getRandomQuote() {
        return ResponseEntity.ok(quoteService.getRandomQuote());

    }

    /**
     * *
     * @param id_quote
     * @param principal
     */
    @GetMapping(value = "/like/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void voteLike(@PathVariable(value = "id") Long id_quote, Principal principal) {
        scoreService.voteLike(id_quote, userService.getByUsername(principal.getName()).getId());

    }

    /**
     * *
     * @param id_quote
     * @param principal
     */
    @GetMapping(value = "/dislike/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void voteDislike(@PathVariable(value = "id") Long id_quote, Principal principal) {
        scoreService.voteDislike(id_quote, userService.getByUsername(principal.getName()).getId());

    }

    /**
     * *
     * @param username
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link Profile}
     */
    @GetMapping(value = "/profile/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable(value = "username") String username) {
        return ResponseEntity.ok(userService.getProfile(username));

    }

    /**
     * *
     * @param request
     * @param principal
     * @return <b>Response code</b> 200<br>
     *         <b>Body</b>: {@link Quote}
     */
    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createQuote(@RequestBody QuoteRequest request, Principal principal) {
        return ResponseEntity.ok(quoteService.createNewQuote(principal, request));

    }

    /**
     * *
     * @param id
     * @param principal
     * @param request
     * @return <b>Response code</b>: 200<br>
     *         <b>Body</b>: {@link Quote}
     */
    @PreAuthorize("@A.isAuthor( #principal, #id )")
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<?> editQuote(@PathVariable(value = "id") Long id, Principal principal, @RequestBody QuoteRequest request) {
        return ResponseEntity.ok(quoteService.editQuote(request, id, principal));

    }

    /**
     * *
     * @param id
     * @param principal
     */
    @PreAuthorize("@A.isAuthor( #principal, #id )")
    @DeleteMapping(value = "/remove/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteQuote(@PathVariable(value = "id") Long id, Principal principal) {
        quoteService.delQuote(id);

    }
}
