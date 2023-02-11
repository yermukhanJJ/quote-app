package com.kameleoon.trial.task.quote.service.specification;

import com.kameleoon.trial.task.quote.entity.Quote;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification for filter
 * @author Nurkali Yermukhan* *
 */
public class QuoteSpec {
    public static Specification<Quote> titleContains(String word) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), word + "%");

    }
}
