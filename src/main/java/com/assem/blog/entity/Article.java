package com.assem.blog.entity;

import com.assem.blog.dto.ArticleDto;
import lombok.*;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "articles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article extends BaseEntity {

    @Column(name = "title")
    @Getter
    private String title;

    @Column(name = "body")
    @Getter
    private String body;

    @Column(name = "desc")
    @Getter
    private String desc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User author;

    @ManyToMany(fetch = FetchType.LAZY
            , cascade = {CascadeType.PERSIST, CascadeType.DETACH
            , CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "article_favorites",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userFavorited = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "article")
    @Getter
    private Set<Comment> comments = new HashSet<>();


    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setArticle(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setArticle(null);
    }

    public ArticleDto asDTO() {
        return new ArticleDto(id, title, body, desc);
    }

    public void update(String title, String body, String desc) {
        this.title = title;
        this.body = body;
        this.desc = desc;
    }
}
