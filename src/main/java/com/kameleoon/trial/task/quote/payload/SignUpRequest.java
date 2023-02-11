package com.kameleoon.trial.task.quote.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto for registration
 * @author Nurkali Yermukhan*
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String username;

    private String password;

    private String email;

}
