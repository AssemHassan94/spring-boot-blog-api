package com.assem.blog.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "articles")
@Entity
public class Article extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private List<Comment> comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Article(String title, String content, User author, List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.comments = comments;
    }

    public Article() {
    }

    public void addComment(Comment tempComment) {
        if (comments == null) {
            comments = new ArrayList<Comment>();
        }
        comments.add(tempComment);
        tempComment.setArticle(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return getTitle().equals(article.getTitle()) && getContent().equals(article.getContent()) && getAuthor().equals(article.getAuthor()) && Objects.equals(getComments(), article.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContent(), getAuthor(), getComments());
    }
}
