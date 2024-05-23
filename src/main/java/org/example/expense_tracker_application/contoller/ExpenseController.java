package org.example.expense_tracker_application.contoller;


import org.example.expense_tracker_application.model.Expense;
import org.example.expense_tracker_application.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseService.save(expense);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.findById(id);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteById(id);
    }

    @PutMapping("/{id}/amount")
    public void updateAmountById(@PathVariable Long id, @RequestBody Double amount) {
        expenseService.updateAmountById(amount, id);
    }

    @DeleteMapping("/category/{category}")
    public void deleteByCategory(@PathVariable String category) {
        expenseService.deleteByCategory(category);
    }

    @PutMapping("/{id}/description")
    public void updateDescriptionById(@PathVariable Long id, @RequestBody String description) {
        expenseService.updateDescriptionById(description, id);
    }

    @PutMapping("/{id}/category")
    public void updateCategoryById(@PathVariable Long id, @RequestBody String category) {
        expenseService.updateCategoryById(category, id);
    }

    @DeleteMapping("/date-range")
    public void deleteByDateRange(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        expenseService.deleteByDateRange(startDate, endDate);
    }

    @PutMapping("/{id}/date")
    public void updateDateById(@PathVariable Long id, @RequestBody LocalDate date) {
        expenseService.updateDateById(date, id);
    }

    @PutMapping("/{id}/user")
    public void updateUserById(@PathVariable Long id, @RequestBody Long userId) {
        expenseService.updateUserById(id, userId);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteByUser(@PathVariable Long userId) {
        expenseService.deleteByUser(userId);
    }

    @PutMapping("/bulk-update")
    public void updateMultipleExpenses(@RequestBody List<Expense> expenses) {
        expenseService.updateMultipleExpenses(expenses);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        expenseService.deleteById(id);
    }

        /* @PathVariable is used to extract the id from the URL.@RequestMapping (and its shorthand variants like @GetMapping,
        @PostMapping, @PutMapping, and @DeleteMapping) is used to map HTTP requests to handler methods. @RequestBody is
        used to bind the HTTP request body to a Java object.*/
}
