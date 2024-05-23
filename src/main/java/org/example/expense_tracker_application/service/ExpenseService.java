package org.example.expense_tracker_application.service;

import org.example.expense_tracker_application.model.Expense;
import org.example.expense_tracker_application.model.User;
import org.example.expense_tracker_application.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense findExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public List<Expense> findAllExpenses() {
        return expenseRepository.findAll();
    }

    @Transactional
    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

    @Transactional
    public void updateAmountById(Double amount, Long id) {
        expenseRepository.updateAmountById(amount, id);
    }

    @Transactional
    public void deleteByCategory(String category) {
        expenseRepository.deleteByCategory(category);
    }

    @Transactional
    public void updateExpense(Long id, Expense expense) {
        Expense existingExpense = expenseRepository.findById(id).orElse(null);
        if (existingExpense != null) {
            existingExpense.setAmount(expense.getAmount());
            existingExpense.setDescription(expense.getDescription());
            existingExpense.setCategory(expense.getCategory());
            existingExpense.setDate(expense.getDate());
            existingExpense.setUser(expense.getUser());
            expenseRepository.save(existingExpense);
        } else {
            System.err.println("Expense not found with ID: " + id);
        }
    }

    @Transactional
    public void updateDescriptionById(String description, Long id) {
        expenseRepository.updateDescriptionById(description, id);
    }

    @Transactional
    public void updateCategoryById(String category, Long id) {
        expenseRepository.updateCategoryById(category, id);
    }

    @Transactional
    public void deleteByDateRange(LocalDate startDate, LocalDate endDate) {
        expenseRepository.deleteByDateRange(startDate, endDate);
    }

    @Transactional
    public void updateDateById(LocalDate date, Long id) {
        expenseRepository.updateDateById(date, id);
    }

    @Transactional
    @Modifying
    public void updateUserById(Long id, Long userId) {
        expenseRepository.updateUserById(id, userId);
    }

    @Transactional
    @Modifying
    public void deleteByUser(Long userId) {
        expenseRepository.deleteByUser(userId);
    }

    @Transactional
    @Modifying
    public void updateMultipleExpenses(List<Long> ids, Double amount) {
        List<Expense> expensesToUpdate = expenseRepository.findAllById(ids);
        for (Expense expense : expensesToUpdate) {
            expense.setAmount(amount);
            expenseRepository.save(expense);
        }
    }
}
