package com.kameleoon.trial.task.quote.repository;

import com.kameleoon.trial.task.quote.entity.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>, PagingAndSortingRepository<Quote,Long> {
    @Query(value = "select * from quote q order by q.score desc LIMIT 10",nativeQuery = true)
    Optional<List<Quote>> getTopQuote();

    @Query(value = "select * from quote q order by q.score asc LIMIT 10",nativeQuery = true)
    Optional<List<Quote>> getFlopQuote();

    @Query(value = "select * from quote q where q.id_user = ?1",nativeQuery = true)
    Optional<List<Quote>> findAllByid_user(Long id);

    Page<Quote> findAll(Specification<Quote> specification, Pageable pageable);

    @Query(value = "select count(*) from quote",nativeQuery = true)
    Long countQuote();

}
