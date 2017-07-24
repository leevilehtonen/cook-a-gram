package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
