package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByPosterInOrderByDateDesc(List<Account> posters);

    List<Post> findByPosterOrderByDateDesc(Account poster);

    Post findTopByPosterOrderByDateDesc(Account poster);


}
