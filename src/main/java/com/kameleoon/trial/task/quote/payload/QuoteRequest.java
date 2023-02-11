package com.kameleoon.trial.task.quote.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Dto for creating,edit operation
 * @author Nurkali Yermukhan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

}
