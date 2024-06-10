package org.example.expense_tracker_application.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDate date;
    @ManyToOne //  indicates that the field user represents a many-to-one relationship
    @JoinColumn(name = "user_id", nullable = false) //This annotation tells JPA that a foreign key column exists in the Expense table to reference the primary key of another table.
    private User user;

    /*
    * The user field in the Expense class represents a foreign key relationship with the User table. Each Expense
    * record must have a corresponding user_id referencing a specific user in the database. This establishes a
    * connection between an expense and the user who incurred it.
    * */

    //Constructors
    public Expense() {
    }

    public Expense(Double amount, String description, String category, LocalDate date) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;

    }
    public Expense(Long id, Double amount, String description, String category, LocalDate date, User user) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.user = user;
    }

    //Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
