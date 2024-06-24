package org.example.expense_tracker_application.repository;

import org.example.expense_tracker_application.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken (String token);

    @Query("SELECT v FROM  VerificationToken v WHERE v.user.username=?1")
    VerificationToken findByUsername(String username);
}
