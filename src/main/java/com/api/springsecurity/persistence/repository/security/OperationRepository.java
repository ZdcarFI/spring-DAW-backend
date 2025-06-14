package com.api.springsecurity.persistence.repository.security;

import com.api.springsecurity.persistence.entity.security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM Operation o where o.permitAll = true")
    List<Operation> findByPubliccAcces();

    Optional<Operation> findByName(String operation);
}
