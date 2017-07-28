package com.leevilehtonen.cookagram.controller;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import com.leevilehtonen.cookagram.repository.RelationshipRepository;
import com.leevilehtonen.cookagram.repository.TagRepository;
import com.leevilehtonen.cookagram.service.AccountService;
import com.leevilehtonen.cookagram.service.LikeService;
import com.leevilehtonen.cookagram.service.PostService;
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
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignup(Account account) {
        return "signup";
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String getFeed(Model model) {
        model.addAttribute("posts", postService.getPostsFromFollowedAccounts());
        model.addAttribute("likes", likeService.getAccountLikedPostIds(accountService.getAuthenticatedAccount()));
        return "feed";
    }

    @RequestMapping(value = "/explore", method = RequestMethod.GET)
    public String getExplore(
            @RequestParam(value = "post", required = false) Long postId,
            @RequestParam(value = "tag", required = false) String tagName,
            Model model) {
        if (postId != null) {
            model.addAttribute("posts", postRepository.findOne(postId));
        } else if (tagName != null) {
            model.addAttribute("posts", postService.getPostsByTag(tagName));
        } else {
            model.addAttribute("posts", postService.getMostLikedPost());
        }
        model.addAttribute("likes", likeService.getAccountLikedPostIds(accountService.getAuthenticatedAccount()));
        return "explore";
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String getPost() {
        return "post";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id == accountService.getAuthenticatedAccount().getId()) {
            return "redirect:/profile";
        }
        Account account = null;
        if (id == null) {
            account = accountService.getAuthenticatedAccount();
        } else {
            account = accountRepository.findOne(id);
            if (account == null) {
                return "redirect:/profile";
            }
            if (relationshipRepository.findTopByFollowerAndFollowed(accountService.getAuthenticatedAccount(), account) != null) {
                model.addAttribute("friend", true);
            } else {
                model.addAttribute("friend", false);
            }
        }

        model.addAttribute("account", account);
        model.addAttribute("followers", accountService.getFollowers(account));
        model.addAttribute("followings", accountService.getFollowings(account));
        model.addAttribute("profileimageurl", accountService.getProfilePictureUrl(account));
        model.addAttribute("posts", postService.getPostsByAccount(account));

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
