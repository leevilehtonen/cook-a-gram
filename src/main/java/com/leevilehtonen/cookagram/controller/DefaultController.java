package com.leevilehtonen.cookagram.controller;

import com.leevilehtonen.cookagram.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("*")
public class DefaultController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getHome(Model model) {
        return "Hello world";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {

        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignup(Account account) {
        return "signup";
    }



}
