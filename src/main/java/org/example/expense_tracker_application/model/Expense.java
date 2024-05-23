package org.example.expense_tracker_application.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "user_id", nullable = false) //This annotation tells JPA that a foreign key column exists in the
    // Expense table to reference the primary key of another table.
    private User user;

    /*
    * The user field in the Expense class represents a foreign key relationship with the User table. Each Expense
    * record must have a corresponding user_id referencing a specific user in the database. This establishes a
    * connection between an expense and the user who incurred it.
    * */


}
