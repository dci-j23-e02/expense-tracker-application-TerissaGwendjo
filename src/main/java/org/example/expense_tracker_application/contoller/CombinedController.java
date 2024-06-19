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
public class CombinedController {

    @Autowired // Injects the UserService dependency
    private UserService userService;

    @Autowired // Injects the ExpenseService dependency
    private ExpenseService expenseService;

    // what makes these dependencies as bean is the annotation @Service
    //so any instance of those classes will be considered a bean in our spring container

    @GetMapping("/") // Maps GET requests for / to this method
    public String homePage(Model model) {
        model.addAttribute("message", "Welcome to the Expense Management System"); // Adds a message to the model
        return "home"; // Returns the view name "home"
    }

    // User Management
    @GetMapping("/users") // Maps GET requests for /users to this method
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers()); // Adds a list of all users to the model
        return "user-list"; // Returns the view name "user-list"
    }

    @GetMapping("/users/new") // Maps GET requests for /users/new to this method
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User()); // Adds a new User object to the model
        return "create-user"; // Returns the view name "create-user"
    }

    @PostMapping("/users") // Maps POST requests for /users to this method
    public String createUser(@ModelAttribute User user, Model model) {
        System.out.println("User email: " + user.getEmail());
        boolean isUserSaved = userService.saveUser(user);
        if (!isUserSaved){
            model.addAttribute("errorMessage", "Failed to send verification e-mail. Please add a valid email address!");
            return "redirect:/create-user";
        }
        return "redirect:/login";
    }

    @GetMapping("/users/edit/{id}") // Maps GET requests for /users/edit/{id} to this method
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id); // Finds the user by ID
        model.addAttribute("user", user); // Adds the user to the model
        return "edit-user"; // Returns the view name "edit-user"
    }

    @PostMapping("/users/update/{id}") // Maps POST requests for /users/update/{id} to this method
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        userService.updateUser(id, user); // Updates the user
        return "redirect:/users"; // Redirects to the list of users
    }

    @GetMapping("/users/delete/{id}") // Maps GET requests for /users/delete/{id} to this method
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Deletes the user by ID
        return "redirect:/users"; // Redirects to the list of users
    }

    @GetMapping("/verify")
    public String verifyUser (@RequestParam("token") String token) {
        userService.verifyUser(token);
        return "redirect:/login?verified";
    }

    // Expense Management

    @GetMapping("/expenses") // Maps GET requests for /expenses to this method
    public String getAllExpenses(Model model) {
        model.addAttribute("expenses", expenseService.findAllExpenses()); // Adds a list of all expenses to the model
        return "expense-list"; // Returns the view name "expense-list"
    }

    @GetMapping("/expenses/new") // Maps GET requests for /expenses/new to this method
    public String showCreateExpenseForm(Model model) {
        model.addAttribute("expense", new Expense()); // Adds a new Expense object to the model
        model.addAttribute("users", userService.findAllUsers()); // Adds a list of all users to the model
        return "create-expense"; // Returns the view name "create-expense"
    }

    @PostMapping("/expenses") // Maps POST requests for /expenses to this method
    public String createExpense(@ModelAttribute Expense expense) {
        expenseService.saveExpense(expense); // Saves the new expense
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @GetMapping("/expenses/edit/{id}") // Maps GET requests for /expenses/edit/{id} to this method
    public String showEditExpenseForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.findExpenseById(id); // Finds the expense by ID
        model.addAttribute("expense", expense); // Adds the expense to the model
        model.addAttribute("users", userService.findAllUsers()); // Adds a list of all users to the model
        return "edit-expense"; // Returns the view name "edit-expense"
    }

    @PostMapping("/expenses/update/{id}") // Maps POST requests for /expenses/update/{id} to this method
    public String updateExpense(@PathVariable Long id, @ModelAttribute Expense expense) {
        expenseService.updateExpense(id, expense); // Updates the expense
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @GetMapping("/expenses/delete/{id}") // Maps GET requests for /expenses/delete/{id} to this method
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpenseById(id); // Deletes the expense by ID
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-amount/{id}") // Maps POST requests for /expenses/update-amount/{id} to this method
    public String updateExpenseAmount(@PathVariable Long id, @RequestParam Double amount) {
        expenseService.updateAmountById(amount, id); // Updates the amount of the expense
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/delete-category") // Maps POST requests for /expenses/delete-category to this method
    public String deleteExpenseByCategory(@RequestParam String category) {
        expenseService.deleteByCategory(category); // Deletes expenses by category
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-description/{id}") // Maps POST requests for /expenses/update-description/{id} to this method
    public String updateExpenseDescription(@PathVariable Long id, @RequestParam String description) {
        expenseService.updateDescriptionById(description, id); // Updates the description of the expense
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-category/{id}") // Maps POST requests for /expenses/update-category/{id} to this method
    public String updateExpenseCategory(@PathVariable Long id, @RequestParam String category) {
        expenseService.updateCategoryById(category, id); // Updates the category of the expense
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/delete-date-range") // Maps POST requests for expenses/delete-date-range to this method
    public String deleteExpensesByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        expenseService.deleteByDateRange(startDate, endDate); // Deletes expenses within a date range
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-date/{id}") // Maps POST requests for /expenses/update-date/{id} to this method
    public String updateExpenseDate(@PathVariable Long id, @RequestParam LocalDate date) {
        expenseService.updateDateById(date, id); // Updates the date of the expense
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-user/{id}") // Maps POST requests for /expenses/update-user/{id} to this method
    public String updateExpenseUser(@PathVariable Long id, @RequestBody User userId) {
        expenseService.updateUserById(id, userId.getId()); // Updates the user of the expense
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/delete-user/{userId}") // Maps POST requests for /expenses/delete-user/{userId} to this method
    public String deleteExpensesByUser(@PathVariable Long userId) {
        expenseService.deleteByUser(userId); // Deletes expenses by user ID
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/update-multiple") // Maps POST requests for /expenses/update-multiple to this method
    public String updateMultipleExpenses(@RequestBody List<Long> ids, @RequestParam Double amount) {
        expenseService.updateMultipleExpenses(ids, amount); // Updates the amount for multiple expenses
        return "redirect:/expenses"; // Redirects to the list of expenses
    }

    @PostMapping("/expenses/delete/{id}") // Maps POST requests for /expenses/delete/{id} to this method
    public String deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteExpenseById(id); // Deletes the expense by ID
        return "redirect:/expenses"; // Redirects to the list of expenses
    }
     //Login and logout endpoints
     @GetMapping("/login")
     public String showLoginForm( Model model) {
         // Adds a default error message to the model
         model.addAttribute("errorMessage", "Invalid credentials");

         // Returns the "login" view
         return "login"; // return a string with the name of the html file
     }

    @GetMapping("/logout")
    public String logout() {

        return "redirect:/login?logout"; // Redirect to login page with logout parameter
    }




    /* @PathVariable is used to extract the id from the URL.@RequestMapping (and its shorthand variants like @GetMapping,
       @PostMapping, @PutMapping, and @DeleteMapping) is used to map HTTP requests to handler methods. @RequestBody is
       used to bind the HTTP request body to a Java object.*/
}
