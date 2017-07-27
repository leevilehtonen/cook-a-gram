package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.ImageEntity;
import com.leevilehtonen.cookagram.domain.Post;
import com.leevilehtonen.cookagram.domain.Tag;
import com.leevilehtonen.cookagram.repository.ImageRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import com.leevilehtonen.cookagram.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    public void createPost(MultipartFile file, String tags) throws IOException {

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(file.getOriginalFilename());
        imageEntity.setMediaType(file.getContentType());
        imageEntity.setSize(file.getSize());
        imageEntity.setContent(file.getBytes());
        imageRepository.save(imageEntity);

        Set<Tag> tagsSet = saveAndLoadTags(tags);

        Post post = new Post();
        post.setImage(imageEntity);
        post.setPoster(accountService.getAuthenticatedAccount());
        post.setTags(tagsSet);
        postRepository.save(post);

    }

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

    public List<Post[]> getPostsByAccount(Account account) {
        List<Post> posts = postRepository.findByPosterOrderByDateAsc(account);
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
}
