package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.*;
import com.leevilehtonen.cookagram.repository.ImageRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import com.leevilehtonen.cookagram.repository.RelationshipRepository;
import com.leevilehtonen.cookagram.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Post service for posts related functionality
 *
 * @author lleevi
 */
@Service
public class PostService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    /**
     * Creates new post. Starts by parsing the image fileurl to byte array. Then sets name, mediatype, size and content. Crates a set of tags from string and saves them to database if tehy doesn't exists. Combines tags and image to a post and saves it to database
     *
     * @param file Base64 image fileurl object
     * @param tags List of comma-separated tag strings
     * @throws IOException
     */
    @Transactional
    public void createPost(String file, String tags, Account a) throws IOException {

        ImageEntity imageEntity = new ImageEntity();
        byte[] imagedata = DatatypeConverter.parseBase64Binary(file.substring(file.indexOf(",") + 1));

        imageEntity.setName("upload.jpeg");
        imageEntity.setMediaType("image/jpeg");
        imageEntity.setSize(new Long(imagedata.length));
        imageEntity.setContent(imagedata);
        imageRepository.save(imageEntity);

        Set<Tag> tagsSet = saveAndLoadTags(tags);

        Post post = new Post();
        post.setImage(imageEntity);
        post.setPoster(a);
        post.setTags(tagsSet);
        postRepository.save(post);

    }

    @Transactional
    public void createPost(String file, String tags) throws IOException {
        createPost(file, tags, accountService.getAuthenticatedAccount());
    }

    /**
     * Splits the tags using commas, saves them to database if they don't exists
     *
     * @param tags comma-separated string of tags
     * @return set of tags which are persisted in database
     */
    @Transactional
    public Set<Tag> saveAndLoadTags(String tags) {
        String[] tagStrings = tags.split(",");
        Set<Tag> tagSet = new HashSet<>();
        for (String tag : tagStrings) {
            Tag t = tagRepository.findByName(tag);
            if (t == null) {
                t = new Tag();
                t.setName(tag);
                t = tagRepository.save(t);
            }
            tagSet.add(t);
        }
        return tagSet;
    }

    /**
     * Gets the posts from server and transforms them to 3 by X grid (WxH) (to easilly add them as a grid to profile page)
     * @param account target account
     * @return List<Post[]> object of posts
     */
    @Transactional
    public List<Post[]> getPostsByAccount(Account account) {
        List<Post> posts = postRepository.findByPosterOrderByDateDesc(account);
        List<Post[]> postgrid = new ArrayList<>();

        int c = 0;
        while (c < posts.size()) {
            Post[] postRow = new Post[3];
            for (int j = 0; j < 3 && c < posts.size(); j++) {
                postRow[j] = posts.get(c);
                c++;
            }
            postgrid.add(postRow);
            postRow = new Post[3];
        }
        return postgrid;

    }

    /**
     * Gets the posts from accounts that the account who is logged in is following
     * @return list of posts from followed accounts
     */
    @Transactional
    public List<Post> getPostsFromFollowedAccounts() {
        List<Account> followedAccounts = new ArrayList<>();
        Set<Relationship> relationships = relationshipRepository.findByFollower(accountService.getAuthenticatedAccount());
        if (relationships == null) {
            return new ArrayList<>();
        }
        relationships.forEach(relationship -> followedAccounts.add(relationship.getFollowed()));
        return postRepository.findByPosterInOrderByDateDesc(followedAccounts);
    }

    /**
     * Gets the posts in order of most likes
     * @return list of post
     */
    @Transactional
    public List<Post> getMostLikedPost() {
        return postRepository.findMostLiked();
    }

    /**
     * Finds posts by a tag
     * @param tagName target tag
     * @return list of post which has a target tag
     */
    @Transactional
    public Set<Post> getPostsByTag(String tagName) {
        Tag tag = tagRepository.findByName(tagName);
        if (tag == null) {
            return new HashSet<>();
        }
        return tag.getPosts();
    }
}
