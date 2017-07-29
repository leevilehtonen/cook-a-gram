package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Role repository
 *
 * @author lleevi
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    /**
     * Finds role by target name
     * @param name target
     * @return Role object
     */
    Role findByName(String name);
}
