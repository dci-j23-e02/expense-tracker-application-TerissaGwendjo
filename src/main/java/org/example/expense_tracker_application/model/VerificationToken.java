package org.example.expense_tracker_application.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "verification-tokens")
public class VerificationToken {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token; // Every user has a token. There is no user without a token. It s a one-to-one relationship

    @OneToOne (targetEntity = User.class, fetch = FetchType.EAGER)
    //the user will be fetched immediately once the verification token has been loaded
    // lazy means it will only be loaded when asked for it and not automatically like EAGER
    @JoinColumn (nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    //Constructors
    public VerificationToken() {
    }

    public VerificationToken(Long id, String token, User user, LocalDateTime expiryDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public VerificationToken(String token, User user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    //Getters and setters

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
