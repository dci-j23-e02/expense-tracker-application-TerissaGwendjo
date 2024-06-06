package org.example.expense_tracker_application.contoller;


import org.example.expense_tracker_application.model.Expense;
import org.example.expense_tracker_application.service.ExpenseService;
import org.example.expense_tracker_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.example.expense_tracker_application.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller // Marks this class as a Spring MVC controller
@RequestMapping("/management") // serves as base for all endpoints and maps requests starting with /management to this controller
public class CombinedController {

    @Autowired // Injects the UserService dependency
    private UserService userService;

    @Autowired // Injects the ExpenseService dependency
    private ExpenseService expenseService;

    @GetMapping("/") // Maps GET requests for /management/ to this method
    public String homePage(Model model) {
        model.addAttribute("message", "Welcome to the Expense Management System"); // Adds a message to the model
        return "home"; // Returns the view name "home"
    }

    // User Management
    @GetMapping("/users") // Maps GET requests for /management/users to this method
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers()); // Adds a list of all users to the model
        return "user-list"; // Returns the view name "user-list"
    }

    @GetMapping("/users/new") // Maps GET requests for /management/users/new to this method
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User()); // Adds a new User object to the model
        return "create-user"; // Returns the view name "create-user"
    }

    @PostMapping("/users") // Maps POST requests for /management/users to this method
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user); // Saves the new user
        return "redirect:/management/users"; // Redirects to the list of users
    }

    @GetMapping("/users/edit/{id}") // Maps GET requests for /management/users/edit/{id} to this method
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id); // Finds the user by ID
        model.addAttribute("user", user); // Adds the user to the model
        return "edit-user"; // Returns the view name "edit-user"
    }

    @PostMapping("/users/update/{id}") // Maps POST requests for /management/users/update/{id} to this method
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        userService.updateUser(id, user); // Updates the user
        return "redirect:/management/users"; // Redirects to the list of users
    }

    @GetMapping("/users/delete/{id}") // Maps GET requests for /management/users/delete/{id} to this method
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Deletes the user by ID
        return "redirect:/management/users"; // Redirects to the list of users
    }

    // Expense Management

    @GetMapping("/expenses") // Maps GET requests for /management/expenses to this method
    public String getAllExpenses(Model model) {
        model.addAttribute("expenses", expenseService.findAllExpenses()); // Adds a list of all expenses to the model
        return "expense-list"; // Returns the view name "expense-list"
    }

    @GetMapping("/expenses/new") // Maps GET requests for /management/expenses/new to this method
    public String showCreateExpenseForm(Model model) {
        model.addAttribute("expense", new Expense()); // Adds a new Expense object to the model
        model.addAttribute("users", userService.findAllUsers()); // Adds a list of all users to the model
        return "create-expense"; // Returns the view name "create-expense"
    }

    @PostMapping("/expenses") // Maps POST requests for /management/expenses to this method
    public String createExpense(@ModelAttribute Expense expense) {
        expenseService.saveExpense(expense); // Saves the new expense
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @GetMapping("/expenses/edit/{id}") // Maps GET requests for /management/expenses/edit/{id} to this method
    public String showEditExpenseForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.findExpenseById(id); // Finds the expense by ID
        model.addAttribute("expense", expense); // Adds the expense to the model
        model.addAttribute("users", userService.findAllUsers()); // Adds a list of all users to the model
        return "edit-expense"; // Returns the view name "edit-expense"
    }

    @PostMapping("/expenses/update/{id}") // Maps POST requests for /management/expenses/update/{id} to this method
    public String updateExpense(@PathVariable Long id, @ModelAttribute Expense expense) {
        expenseService.updateExpense(id, expense); // Updates the expense
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @GetMapping("/expenses/delete/{id}") // Maps GET requests for /management/expenses/delete/{id} to this method
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpenseById(id); // Deletes the expense by ID
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-amount/{id}") // Maps POST requests for /management/expenses/update-amount/{id} to this method
    public String updateExpenseAmount(@PathVariable Long id, @RequestParam Double amount) {
        expenseService.updateAmountById(amount, id); // Updates the amount of the expense
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/delete-category") // Maps POST requests for /management/expenses/delete-category to this method
    public String deleteExpenseByCategory(@RequestParam String category) {
        expenseService.deleteByCategory(category); // Deletes expenses by category
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-description/{id}") // Maps POST requests for /management/expenses/update-description/{id} to this method
    public String updateExpenseDescription(@PathVariable Long id, @RequestParam String description) {
        expenseService.updateDescriptionById(description, id); // Updates the description of the expense
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-category/{id}") // Maps POST requests for /management/expenses/update-category/{id} to this method
    public String updateExpenseCategory(@PathVariable Long id, @RequestParam String category) {
        expenseService.updateCategoryById(category, id); // Updates the category of the expense
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/delete-date-range") // Maps POST requests for /management/expenses/delete-date-range to this method
    public String deleteExpensesByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        expenseService.deleteByDateRange(startDate, endDate); // Deletes expenses within a date range
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-date/{id}") // Maps POST requests for /management/expenses/update-date/{id} to this method
    public String updateExpenseDate(@PathVariable Long id, @RequestParam LocalDate date) {
        expenseService.updateDateById(date, id); // Updates the date of the expense
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-user/{id}") // Maps POST requests for /management/expenses/update-user/{id} to this method
    public String updateExpenseUser(@PathVariable Long id, @RequestBody User userId) {
        expenseService.updateUserById(id, userId.getId()); // Updates the user of the expense
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/delete-user/{userId}") // Maps POST requests for /management/expenses/delete-user/{userId} to this method
    public String deleteExpensesByUser(@PathVariable Long userId) {
        expenseService.deleteByUser(userId); // Deletes expenses by user ID
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-multiple") // Maps POST requests for /management/expenses/update-multiple to this method
    public String updateMultipleExpenses(@RequestBody List<Long> ids, @RequestParam Double amount) {
        expenseService.updateMultipleExpenses(ids, amount); // Updates the amount for multiple expenses
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/delete/{id}") // Maps POST requests for /management/expenses/delete/{id} to this method
    public String deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteExpenseById(id); // Deletes the expense by ID
        return "redirect:/management/expenses"; // Redirects to the list of expenses
    }

    /* @PathVariable is used to extract the id from the URL.@RequestMapping (and its shorthand variants like @GetMapping,
       @PostMapping, @PutMapping, and @DeleteMapping) is used to map HTTP requests to handler methods. @RequestBody is
       used to bind the HTTP request body to a Java object.*/
}
