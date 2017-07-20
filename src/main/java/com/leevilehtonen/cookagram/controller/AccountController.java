package com.leevilehtonen.cookagram.controller;


import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.POST)
    public String postAccounts(@Valid @ModelAttribute Account account, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        accountService.save(account);

        return "redirect:/index";
    }


}
