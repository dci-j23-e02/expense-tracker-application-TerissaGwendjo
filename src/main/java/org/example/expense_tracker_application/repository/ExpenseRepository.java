package org.example.expense_tracker_application.repository;


import org.example.expense_tracker_application.model.Expense;
import org.example.expense_tracker_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Modifying
    @Query("update Expense e set e.amount = :amount where e.id = :id")
    void updateAmountById(@Param("amount") Double amount, @Param("id") Long id);

    @Modifying
    @Query("delete from Expense e where e.category = :category")
    void deleteByCategory(@Param("category") String category);

    @Modifying
    @Query("update Expense e set e.description = :description where e.id = :id")
    void updateDescriptionById(@Param("description") String description, @Param("id") Long id);

    @Modifying
    @Query("update Expense e set e.category = :category where e.id = :id")
    void updateCategoryById(@Param("category") String category, @Param("id") Long id);

    @Modifying
    @Query("delete from Expense e where e.date between :startDate and :endDate")
    void deleteByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Modifying
    @Query("update Expense e set e.date = :date where e.id = :id")
    void updateDateById(@Param("date") LocalDate date, @Param("id") Long id);

    @Modifying
    @Query("update Expense e set e.user.id = :userId where e.id = :id")
    void updateUserById(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Query("delete from Expense e where e.user.id = :userId")
    void deleteByUser(@Param("userId") Long userId);
}
