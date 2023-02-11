package com.kameleoon.trial.task.quote.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Dto for getting profile by username
 * @author Nurkali Yermukhan*
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @NotNull
    private String username;

    @NotNull
    private String email;

    private LocalDateTime createAt;

}
