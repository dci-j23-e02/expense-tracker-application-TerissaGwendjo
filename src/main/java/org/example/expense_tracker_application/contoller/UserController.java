package org.example.expense_tracker_application.contoller;

import org.example.expense_tracker_application.model.User;
import org.example.expense_tracker_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // serves as a base path for the controller. it applies to all methods in the controller.
//at the class level means all endpoints in this controller will be prefixed with /api/expenses.

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) { // Binds the body of the HTTP request to a method parameter.
        // It is used to parse JSON or XML request bodies into Java objects.
        return userService.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) { // The corresponding value provided in the URL path is then mapped
        // to a method parameter annotated with @PathVariable. Used in RESTful endpoints to extract values from the URL path.
        return userService.findById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    /*
    * @PathVariable is used to extract the id from the URL.
      @RequestMapping (and its shorthand variants like @GetMapping, @PostMapping, @PutMapping, and @DeleteMapping) is
      used to map HTTP requests to handler methods. @RequestBody is used to bind the HTTP request body to a Java object.*/

}

