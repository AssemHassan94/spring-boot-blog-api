package com.assem.blog.entity;

import com.assem.blog.dto.ArticleDto;
import com.assem.blog.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Builder
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Column(name = "body")
    @Getter
    private String body;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH
            , CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User author;

    public CommentDto asDTO() {
        return new CommentDto(id, body);
    }

    public void update(String body) {
        this.body = body;
    }

    public Comment() {
    }
}
