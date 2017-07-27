package com.leevilehtonen.cookagram.controller;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import com.leevilehtonen.cookagram.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignup(Account account) {
        return "signup";
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String getFeed() {
        return "feed";
    }

    @RequestMapping(value = "/explore", method = RequestMethod.GET)
    public String getExplore(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "explore";
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String getPost() {
        return "post";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(@RequestParam(value = "name", required = false) String name, Model model) {
        Account account = null;
        if (name == null) {
            account = accountService.getAuthenticatedAccount();
        } else {
            account = accountRepository.findByUsername(name);
        }
        model.addAttribute("account", account);

        return "profile";
    }

    @RequestMapping(value = "*", method = RequestMethod.GET)
    public String getDefault() {
        if (accountService.getAuthenticatedAccount() == null) {
            return "redirect:/explore";
        } else {
            return "redirect:/feed";
        }

    }



}
