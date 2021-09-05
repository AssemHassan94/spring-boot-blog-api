package com.assem.blog.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment extends BaseEntity {

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH
            , CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH
            , CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User author;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Comment(String comment, Article article, User author) {
        this.comment = comment;
        this.article = article;
        this.author = author;
    }

    public Comment() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment1 = (Comment) o;
        return getComment().equals(comment1.getComment()) && getArticle().equals(comment1.getArticle()) && getAuthor().equals(comment1.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComment(), getArticle(), getAuthor());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", article=" + article +
                ", author=" + author +
                '}';
    }
}
