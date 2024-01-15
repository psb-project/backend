package com.backend.project.psb.repository;

import com.backend.project.psb.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepo extends JpaRepository<CreditCard,Long> {
    Optional<List<CreditCard>> findByUserId(Long userId);
}
