package com.kameleoon.trial.task.quote.service;

import com.kameleoon.trial.task.quote.entity.Quote;
import com.kameleoon.trial.task.quote.entity.Score;
import com.kameleoon.trial.task.quote.entity.Users;
import com.kameleoon.trial.task.quote.repository.QuoteRepository;
import com.kameleoon.trial.task.quote.repository.ScoreRepository;
import com.kameleoon.trial.task.quote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Service for voting operations
 * @author Nurkali Yermukhan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final ScoreRepository scoreRepository;

    @Transactional
    public void voteLike(Long id_quote, Long id_user) {
        Users user = userRepository.findById(id_user)
                .orElseThrow(() -> new UsernameNotFoundException("User with id: " + id_user + " not found!"));
        Quote quote = quoteRepository.findById(id_quote)
                .orElseThrow(() -> new EntityNotFoundException("Quote with id: " + id_quote + " not found!"));

        if (scoreRepository.existsByIdUserAndIdQuote(id_user, id_quote) && scoreRepository.findByIdUserAndIdQuote(id_user, id_quote).getVoice()) {
            log.error("The user has already voiced.");
        } else if (scoreRepository.existsByIdUserAndIdQuote(id_user, id_quote) && !scoreRepository.findByIdUserAndIdQuote(id_user, id_quote).getVoice()) {
            Score score = scoreRepository.findByIdUserAndIdQuote(id_user, id_quote);
            score.setVoice(true);
            quote.setScore(quote.getScore() + 1);
            quoteRepository.save(quote);
            log.info("User: " + user.getUsername() + " voiced for quote: " + quote.getTitle());
            scoreRepository.save(score);
        } else {
            Score score = new Score(user.getId(), quote.getId(), true);
            quote.setScore(quote.getScore() + 1);
            quoteRepository.save(quote);
            log.info("User: " + user.getUsername() + " voiced for quote: " + quote.getTitle());
            scoreRepository.save(score);
        }
    }

    @Transactional
    public void voteDislike(Long id_quote, Long id_user) {
        Users user = userRepository.findById(id_user)
                .orElseThrow(() -> new UsernameNotFoundException("User with id: " + id_user + " not found!"));
        Quote quote = quoteRepository.findById(id_quote)
                .orElseThrow(() -> new EntityNotFoundException("Quote with id: " + id_quote + " not found!"));
        if (scoreRepository.existsByIdUserAndIdQuote(id_user, id_quote) && scoreRepository.findByIdUserAndIdQuote(id_user, id_quote).getVoice()) {
            Score score = scoreRepository.findByIdUserAndIdQuote(id_user, id_quote);
            score.setVoice(false);
            quote.setScore(quote.getScore() - 1);
            quoteRepository.save(quote);
            log.info("User: " + user.getUsername() + " voiced for quote: " + quote.getTitle());
            scoreRepository.save(score);
        } else if (scoreRepository.existsByIdUserAndIdQuote(id_user, id_quote) && !scoreRepository.findByIdUserAndIdQuote(id_user, id_quote).getVoice()) {
            log.error("The user has already voiced.");
        } else {
            Score score = new Score(user.getId(), quote.getId(), false);
            quote.setScore(quote.getScore() - 1);
            quoteRepository.save(quote);
            log.info("User: " + user.getUsername() + " voiced for quote: " + quote.getTitle());
            scoreRepository.save(score);
        }
    }
}
