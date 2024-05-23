package org.example.expense_tracker_application.repository;

import org.example.expense_tracker_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> { // the entity type the repository manages (User)
    // and the data type used for the primary key (ID) of the User entity. Here, it's specified as Long.

}
