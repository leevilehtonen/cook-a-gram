package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findByName(String name);
}
