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

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountUniqueValidator accountUniqueValidator;

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
