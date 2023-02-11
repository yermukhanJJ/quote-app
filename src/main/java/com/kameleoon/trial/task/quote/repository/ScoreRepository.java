package com.kameleoon.trial.task.quote.repository;

import com.kameleoon.trial.task.quote.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Boolean existsByIdUserAndIdQuote(Long id_user, Long id_quote);

    Score findByIdUserAndIdQuote(Long id_user, Long id_quote);

    @Modifying
    @Query(value = "delete from user_score where id_user = ?1 and id_quote = ?2", nativeQuery = true)
    void deleteScore(Long id_user, Long id_quote);

    @Modifying
    @Query(value = "delete from user_score where id_quote = ?1", nativeQuery = true)
    void deleteAllByIdQuote(Long id_quote);

}
