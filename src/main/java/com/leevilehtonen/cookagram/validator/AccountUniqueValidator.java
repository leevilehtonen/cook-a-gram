package com.leevilehtonen.cookagram.validator;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Custom validator for validating uniqueness of name and email from database
 *
 * @author lleevi
 */
@Component
public class AccountUniqueValidator implements Validator {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.equals(aClass);
    }

    /**
     * Checks from database if there exists any account with same username or email
     * @param o the account object
     * @param errors the errors object
     * @see Account
     */
    @Override
    public void validate(Object o, Errors errors) {
        Account account = (Account) o;
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate");
        }

        if (accountRepository.findByEmail(account.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate");
        }
    }
}
