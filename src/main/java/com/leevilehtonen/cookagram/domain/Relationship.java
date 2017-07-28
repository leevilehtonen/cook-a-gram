package com.leevilehtonen.cookagram.domain;


import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Relationship extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "follower")
    private Account follower;

    @ManyToOne
    @JoinColumn(name = "followed")
    private Account followed;

    public Account getFollower() {
        return follower;
    }

    public void setFollower(Account follower) {
        this.follower = follower;
    }

    public Account getFollowed() {
        return followed;
    }

    public void setFollowed(Account followed) {
        this.followed = followed;
    }
}
