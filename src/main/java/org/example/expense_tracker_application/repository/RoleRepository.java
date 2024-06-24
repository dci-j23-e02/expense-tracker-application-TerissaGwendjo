package org.example.expense_tracker_application.repository;

import org.example.expense_tracker_application.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long>{
    Role findByName (String name);
}
