package org.example.expense_tracker_application.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name="user_id") // ensures id field in your User class maps to a column named user_id in the database table.
        private Long id;

        @Column(nullable = false, unique = true)
        private String username;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false, unique = true)
        private String email;

        @OneToMany(mappedBy = "user") // signifies a one-to-many relationship between the class it's applied to
        //(in this case, the User class) and another entity (the Expense class).

       private Set<Expense> expenses; // defines a collection of Expense objects associated with a single User instance.
    //  It establishes a one-to-many relationship between users and their expenses. A single user can have many expenses,
    //  and these expenses are stored within the expenses set.
    @Column(nullable = false)
    private boolean verified = false; // so from here we ask what user has been verified


    // Constructors
        public User() {
        }
        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }

    public User(Long id, String username, String password, String email, Set<Expense> expenses) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.expenses = expenses;
    }

    // Getters and setters
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public Set<Expense> getExpenses() {
        return expenses;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

        /*
        * The @OneToMany(mappedBy = "user") private Set<Expense> expenses; annotation in the User class tells JPA that:
          The User class has a one-to-many relationship with the Expense class.
          JPA should look for a field named user (or potentially user with @ManyToOne) in the Expense class that manages this relationship.*/


    }

