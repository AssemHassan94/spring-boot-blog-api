package com.assem.blog.entity;

import com.assem.blog.dto.ArticleDto;
import com.assem.blog.dto.CommentDto;
import lombok.*;

import javax.persistence.*;



@Entity
@Table(name = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Column(name = "body")
    @Getter
    private String body;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @Setter
    private Article article;


    public CommentDto asDTO() {
        return new CommentDto(id, body);
    }

    public void update(String body) {
        this.body = body;
    }

}
