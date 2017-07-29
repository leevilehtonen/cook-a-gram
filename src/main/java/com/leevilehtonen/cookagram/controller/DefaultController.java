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

/**
 * Controller listening requests to all main navigation endpoints
 *
 * @author lleevi
 */
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

    /**
     * Get login page
     * @return login
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

    /**
     * Get sigunp page
     * @return signup
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignup(Account account) {
        return "signup";
    }

    /**
     * Get feed page. Attaches posts and likes to model
     * @return feed
     */
    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String getFeed(Model model) {
        model.addAttribute("posts", postService.getPostsFromFollowedAccounts());
        model.addAttribute("likes", likeService.getAccountLikedPostIds(accountService.getAuthenticatedAccount()));
        return "feed";
    }

    /**
     * Get explore page. If has param tag then attaches posts with given tag. If has param post attaches one post with given id. Otherwise attaches all posts in most liked order. In any case attaches users likes.
     * @param postId id of the post to look for
     * @param tagName name of the target tag
     * @return explore page
     */
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

    /**
     * Get post page
     * @return post
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String getPost() {
        return "post";
    }

    /**
     * Get profile page. If has a param id the attaches the profile of the account with that id. Also attaches if the loggedin user is following that account, follower count, following count, profile image url and posts of the account.
     * @param id target id of the account
     * @return profile page
     */
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

    /**
     * Return feed if loggedin otherwise explore
     * @return default page
     */
    @RequestMapping(value = "*", method = RequestMethod.GET)
    public String getDefault() {
        if (accountService.getAuthenticatedAccount() == null) {
            return "redirect:/explore";
        } else {
            return "redirect:/feed";
        }

    }


}
