package com.kameleoon.trial.task.quote.service;

import com.kameleoon.trial.task.quote.entity.Quote;
import com.kameleoon.trial.task.quote.entity.Users;
import com.kameleoon.trial.task.quote.payload.QuoteRequest;
import com.kameleoon.trial.task.quote.payload.QuoteResponse;
import com.kameleoon.trial.task.quote.repository.QuoteRepository;
import com.kameleoon.trial.task.quote.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for operations with quote
 * @author Nurkali Yermukhan* *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final UserService userService;
    private final ScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    public List<QuoteResponse> getTop10Quotes() {
        log.info("Getting top 10 quotes!");
        List<Quote> quotes = quoteRepository.getTopQuote()
                .orElseThrow(() -> new EntityNotFoundException("Quotes not found!"));
        List<QuoteResponse> quoteResponseList = new ArrayList<>();
        for (Quote quote : quotes) {
            quoteResponseList.add(new QuoteResponse(quote.getId(), quote.getContent(), quote.getTitle(), quote.getScore(),
                    userService.getById(quote.getId_user()).getUsername(), quote.getCreateAt(), quote.getUpdateAt()));
        }

        return quoteResponseList;
    }

    @Transactional(readOnly = true)
    public List<QuoteResponse> getFlop10Quotes() {
        log.info("Getting flop 10 quotes!");
        List<Quote> quotes = quoteRepository.getFlopQuote()
                .orElseThrow(() -> new EntityNotFoundException("Quotes not found!"));
        List<QuoteResponse> quoteResponseList = new ArrayList<>();
        for (Quote quote : quotes) {
            quoteResponseList.add(new QuoteResponse(quote.getId(), quote.getContent(), quote.getTitle(), quote.getScore(),
                    userService.getById(quote.getId_user()).getUsername(), quote.getCreateAt(), quote.getUpdateAt()));
        }

        return quoteResponseList;
    }

    @Transactional(readOnly = true)
    public QuoteResponse getRandomQuote() {
        int randomId = (int) ((Math.random() * ((quoteRepository.countQuote() - 0))));
        List<QuoteResponse> quoteList = new ArrayList<>();
        List<Quote> quotes = quoteRepository.findAll();
        for (Quote quote : quotes) {
            quoteList.add(new QuoteResponse(quote.getId(), quote.getTitle(), quote.getContent(),
                    quote.getScore(), userService.getById(quote.getId_user()).getUsername(), quote.getCreateAt(), quote.getUpdateAt()));
        }
        return quoteList.get(randomId);
    }

    @Transactional(readOnly = true)
    public List<Quote> getAllByUserId(Long id) {
        List<Quote> quotes = quoteRepository.findAllByid_user(id)
                .orElseThrow(() -> new EntityNotFoundException("Quote not found!"));

        return quotes;

    }

    @Transactional(readOnly = true)
    public Quote getQuoteById(Long id) {
        log.info("Getting quote with id: " + id);
        return quoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quote with id: " + id + " not found!"));
    }

    @Transactional(readOnly = true)
    public List<QuoteResponse> getAllByTitle(Specification<Quote> specification, Pageable pageable) {
        Page<Quote> quotes = quoteRepository.findAll(specification, pageable);
        List<QuoteResponse> quoteResponses = new ArrayList<>();
        for (Quote quote : quotes) {
            quoteResponses.add(new QuoteResponse(quote.getId(), quote.getContent(), quote.getTitle(), quote.getScore(),
                    userService.getById(quote.getId_user()).getUsername(), quote.getCreateAt(), quote.getUpdateAt()));
        }
        return quoteResponses;

    }

    @Transactional
    public Quote createNewQuote(Principal principal, QuoteRequest request) {
        Users user = userService.getByUsername(principal.getName());
        Quote quote = new Quote(request.getTitle(), request.getContent(), 0L, user.getId(), LocalDateTime.now());
        log.info("Created new quote");
        return quoteRepository.save(quote);

    }

    @Transactional
    public Quote editQuote(QuoteRequest request, Long id, Principal principal) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quote with id: " + id + " not found!"));
        if (userService.getByUsername(principal.getName()).getId().equals(quote.getId_user())) {
            quote.setTitle(request.getTitle());
            quote.setContent(request.getContent());
            quote.setUpdateAt(LocalDateTime.now());
        } else log.error("This user: " + principal.getName() + " does not have root.");
        return quoteRepository.save(quote);
    }

    @Transactional
    public void delQuote(Long id) {
        scoreRepository.deleteAllByIdQuote(id);
        quoteRepository.deleteById(id);

    }
}
