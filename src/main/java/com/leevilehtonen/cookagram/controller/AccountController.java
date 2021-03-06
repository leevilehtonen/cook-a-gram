package com.leevilehtonen.cookagram.controller;


import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.service.AccountService;
import com.leevilehtonen.cookagram.validator.AccountUniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Controller listening requests to accounts endpoint
 *
 * @author lleevi
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountUniqueValidator accountUniqueValidator;

    /**
     * Post request to accounts is used for creating new accounts
     * @param account to create
     * @param bindingResult for error checking
     * @param redirectAttributes for redirecting with a message
     * @return redirect or stay in page
     */
    @RequestMapping(method = RequestMethod.POST)
    public String postAccounts(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        accountUniqueValidator.validate(account, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        accountService.save(account);

        redirectAttributes.addFlashAttribute("message", "You have successfully created an account.");
        return "redirect:/login";
    }


}
