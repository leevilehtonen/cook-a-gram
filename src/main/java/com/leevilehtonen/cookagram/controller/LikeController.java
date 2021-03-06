package com.leevilehtonen.cookagram.controller;

import com.leevilehtonen.cookagram.service.AccountService;
import com.leevilehtonen.cookagram.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller listening requests to likes endpoint
 *
 * @author lleevi
 */
@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private AccountService accountService;

    /**
     * Toggles the like of the post
     * @param target post id
     * @return like body
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String toggleLike(@RequestParam(name = "target") Long target) {
        likeService.toggleLike(accountService.getAuthenticatedAccount(), target);
        return "like";
    }

}
