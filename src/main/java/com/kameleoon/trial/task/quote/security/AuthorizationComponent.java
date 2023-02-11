package com.kameleoon.trial.task.quote.security;

import com.kameleoon.trial.task.quote.entity.Quote;
import com.kameleoon.trial.task.quote.entity.Users;
import com.kameleoon.trial.task.quote.repository.QuoteRepository;
import com.kameleoon.trial.task.quote.repository.UserRepository;
import com.kameleoon.trial.task.quote.service.QuoteService;
import com.kameleoon.trial.task.quote.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * Additional component for PreAuthorize function
 * @author Nurkali Yermukhan
 */
@Component("A")
@RequiredArgsConstructor
public class AuthorizationComponent {

    private final UserService userService;
    private final QuoteService quoteService;

    public Boolean isAuthor(@NonNull final Principal principal, @NonNull final Long id) {
        Users user = userService.getByUsername(principal.getName());
        Quote quote = quoteService.getQuoteById(id);
        if (user.getId().equals(quote.getId_user()))
            return true;
        else
            return false;
    }
}
