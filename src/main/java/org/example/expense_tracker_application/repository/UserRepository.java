package org.example.expense_tracker_application.repository;

import org.example.expense_tracker_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository <User, Long> { // the entity type the repository manages (User)
    // and the data type used for the primary key (ID) of the User entity. Here, it's specified as Long.
    User findByUsername(String username); // Method to find a user by username

    User findByEmail(String email);

    @Modifying
    @Query ("UPDATE User u SET u.roles=?2 WHERE u.username =?1")
    void updateUserRoles (String username, Set<String>roles);

}
