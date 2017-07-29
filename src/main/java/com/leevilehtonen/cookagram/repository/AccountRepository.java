package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Account repository
 *
 * @author lleevi
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    /**
     * Finds account by username (as username is unique column we can expect only one or zero result)
     * @param username target
     * @return account object
     */
    Account findByUsername(String username);

    /**
     * Finds account by email (as email is unique column we can expect only one or zero results)
     * @param email target
     * @return account object
     */
    Account findByEmail(String email);
}
