package com.kameleoon.trial.task.quote.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quote")
@Getter
@Setter
@Data
@NoArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "score")
    private Long score;

    @Column(name = "id_user")
    private Long id_user;

    @Column(name = "createat")
    private LocalDateTime createAt;

    @Column(name = "updateat")
    private LocalDateTime updateAt;

    public Quote(String title, String content, Long score, Long id_user, LocalDateTime createAt) {
        this.title = title;
        this.content = content;
        this.score = score;
        this.id_user = id_user;
        this.createAt = createAt;
    }

}
