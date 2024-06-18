package org.example.expense_tracker_application.model;

import jakarta.persistence.*;

@Entity
@Table (name = "verification-tokens")
public class VerificationToken {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String token; // the users have a token each. every user has a token. it s a one-to-one relationship

    @OneToOne (targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "user_id")
    private User user;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
