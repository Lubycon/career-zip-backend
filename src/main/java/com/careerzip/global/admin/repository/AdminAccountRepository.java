package com.careerzip.global.admin.repository;

import com.careerzip.global.admin.entity.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {

    Optional<AdminAccount> findByName(String adminName);
}
