package com.kameleoon.trial.task.quote.util;

import com.kameleoon.trial.task.quote.payload.QuoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for operations with quote
 * @author Nurkali Yermukhan
 */
@Component
@RequiredArgsConstructor
public class QuoteValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return QuoteRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        QuoteRequest quoteRequest = (QuoteRequest) target;

        if (quoteRequest.getContent().equals("") || quoteRequest.getTitle().equals(""))
            errors.rejectValue("title","","Field not null!");

    }
}
