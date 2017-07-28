package com.leevilehtonen.cookagram.controller;

import com.leevilehtonen.cookagram.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String toggleLike(@RequestParam(name = "target") Long target) {
        likeService.toggleLike(target);
        return "like";
    }

}
