package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Tag repository
 *
 * @author lleevi
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
    /**
     * Finds tag object ny its name
     * @param name target
     * @return Tag object
     */
    Tag findByName(String name);
}
