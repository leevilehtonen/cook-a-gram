package com.leevilehtonen.cookagram.controller;

import com.leevilehtonen.cookagram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(method = RequestMethod.POST)
    public String postPost(@RequestParam("file") String file, @RequestParam String tags, RedirectAttributes redirectAttributes) throws IOException {
        postService.createPost(file, tags);

        return "redirect:/explore";
    }

}
