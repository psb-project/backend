package com.backend.project.psb.repository;

import com.backend.project.psb.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByUser_Id(Long userId);
}
