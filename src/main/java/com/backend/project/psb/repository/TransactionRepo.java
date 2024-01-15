package com.backend.project.psb.repository;

import com.backend.project.psb.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transactions,Long> {
    Optional<List<Transactions>> findByCreditCardId(Long creditCardId);
}
