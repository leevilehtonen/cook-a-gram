package com.leevilehtonen.cookagram.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE_LIKE")
public class Like extends AbstractPersistable<Long> {

    @ManyToOne
    private Account liker;

    @ManyToOne
    private Post post;

    public Account getLiker() {
        return liker;
    }

    public void setLiker(Account liker) {
        this.liker = liker;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
