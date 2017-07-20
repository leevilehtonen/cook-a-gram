package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
