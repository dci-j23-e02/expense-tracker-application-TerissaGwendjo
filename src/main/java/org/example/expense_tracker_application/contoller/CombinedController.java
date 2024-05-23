package org.example.expense_tracker_application.contoller;


import org.example.expense_tracker_application.model.Expense;
import org.example.expense_tracker_application.service.ExpenseService;
import org.example.expense_tracker_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.example.expense_tracker_application.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/management")
public class CombinedController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    // User Management

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user-list";
    }

    @GetMapping("/users/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create-user";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/management/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        userService.updateUser(id, user);
        return "redirect:/management/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/management/users";
    }

    // Expense Management

    @GetMapping("/expenses")
    public String getAllExpenses(Model model) {
        model.addAttribute("expenses", expenseService.findAllExpenses());
        return "expense-list";
    }

    @GetMapping("/expenses/new")
    public String showCreateExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("users", userService.findAllUsers());
        return "create-expense";
    }

    @PostMapping("/expenses")
    public String createExpense(@ModelAttribute Expense expense) {
        expenseService.saveExpense(expense);
        return "redirect:/management/expenses";
    }

    @GetMapping("/expenses/edit/{id}")
    public String showEditExpenseForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.findExpenseById(id);
        model.addAttribute("expense", expense);
        model.addAttribute("users", userService.findAllUsers());
        return "edit-expense";
    }

    @PostMapping("/expenses/update/{id}")
    public String updateExpense(@PathVariable Long id, @ModelAttribute Expense expense) {
        expenseService.updateExpense(id, expense);
        return "redirect:/management/expenses";
    }

    @GetMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/update-amount/{id}")
    public String updateExpenseAmount(@PathVariable Long id, @RequestParam Double amount) {
        expenseService.updateAmountById(amount,id);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/delete-category")
    public String deleteExpenseByCategory(@RequestParam String category) {
        expenseService.deleteByCategory(category);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/update-description/{id}")
    public String updateExpenseDescription(@PathVariable Long id, @RequestParam String description) {
        expenseService.updateDescriptionById(description, id);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/update-category/{id}")
    public String updateExpenseCategory(@PathVariable Long id, @RequestParam String category) {
        expenseService.updateCategoryById(category,id);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/delete-date-range")
    public String deleteExpensesByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        expenseService.deleteByDateRange(startDate, endDate);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/update-date/{id}")
    public String updateExpenseDate(@PathVariable Long id, @RequestParam LocalDate date) {
        expenseService.updateDateById(date,id);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/update-user/{id}")
    public String updateExpenseUser(@PathVariable Long id, @RequestBody User userId) {
        expenseService.updateUserById(id, userId.getId());
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/delete-user/{userId}")
    public String deleteExpensesByUser(@PathVariable Long userId) {
        expenseService.deleteByUser(userId);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/update-multiple")
    public String updateMultipleExpenses(@RequestBody List<Long> ids, @RequestParam Double amount) {
        expenseService.updateMultipleExpenses(ids, amount);
        return "redirect:/management/expenses";
    }

    @PostMapping("/expenses/delete/{id}")
    public String deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);
        return "redirect:/management/expenses";
    }
        /* @PathVariable is used to extract the id from the URL.@RequestMapping (and its shorthand variants like @GetMapping,
        @PostMapping, @PutMapping, and @DeleteMapping) is used to map HTTP requests to handler methods. @RequestBody is
        used to bind the HTTP request body to a Java object.*/
}
