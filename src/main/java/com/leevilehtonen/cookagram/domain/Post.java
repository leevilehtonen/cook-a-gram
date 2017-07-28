package com.leevilehtonen.cookagram.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Post extends AbstractPersistable<Long> {

    @ManyToOne
    private Account poster;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(
                    name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags;

    @OneToOne
    private ImageEntity image;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "post")
    private Set<Like> likes;

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Post() {
        this.date = new Date();
    }

    public Account getPoster() {
        return poster;
    }

    public void setPoster(Account poster) {
        this.poster = poster;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public ImageEntity getImage() {
        return image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }
}
