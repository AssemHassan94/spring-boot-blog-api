package com.assem.blog.entity;

import com.assem.blog.dto.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "articles")
@Entity
@Builder
@AllArgsConstructor
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


    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH
            , CascadeType.REFRESH, CascadeType.MERGE})
    private User author;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private List<Comment> comments;


    public void addComment(Comment tempComment) {
        if (comments == null) {
            comments = new ArrayList<Comment>();
        }
        comments.add(tempComment);
    }

    public ArticleDto asDTO() {
        return new ArticleDto(id, title, body, desc);
    }

    public void update(String title, String body, String desc) {
        this.title = title;
        this.body = body;
        this.desc = desc;
    }

    public Article() {
    }
}
