package com.kameleoon.trial.task.quote.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Dto for getting quotes(Top10,Flop10)*
 * @author Nurkali Yermukhan*
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteResponse {

    private Long id;

    private String title;

    private String content;

    private Long score;

    private String username;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
