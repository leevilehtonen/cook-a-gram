package com.leevilehtonen.cookagram.controller;

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


}
