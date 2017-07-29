package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Image repository
 *
 * @author lleevi
 */
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
