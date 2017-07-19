package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
