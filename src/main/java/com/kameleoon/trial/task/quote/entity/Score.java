package com.kameleoon.trial.task.quote.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_score")
@Getter
@Setter
@Data
@NoArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "id_quote")
    private Long idQuote;

    @Column(name = "voice")
    private Boolean voice;

    public Score(Long id_user, Long id_quote, Boolean voice) {
        this.idUser = id_user;
        this.idQuote = id_quote;
        this.voice = voice;
    }

}
