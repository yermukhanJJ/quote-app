package com.kameleoon.trial.task.quote.repository;

import com.kameleoon.trial.task.quote.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    Boolean existsByName(String name);
}
