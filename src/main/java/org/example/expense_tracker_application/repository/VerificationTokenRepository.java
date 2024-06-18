package org.example.expense_tracker_application.repository;

import org.example.expense_tracker_application.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken (String token);
}
