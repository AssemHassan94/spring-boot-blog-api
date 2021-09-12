package com.assem.blog.dao;

import com.assem.blog.entity.Article;
import com.assem.blog.entity.Comment;
import com.assem.blog.entity.User;
import com.assem.blog.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private ArticleRepository underTestArticle;
    @Autowired
    private UserRepository underTestUser;
    @Autowired
    private CommentRepository underTestComment;

    @Test
    void CheckIfCommentExistsId() {
        //given
        User user = User.builder()
                .userName("assem")
                .password("123456")
                .bio("post writer")
                .build();

        underTestUser.save(user);

        Article article = Article.builder()
                .title("assem")
                .body("lolololo")
                .desc("It Article")
                .author(user)
                .build();

        underTestArticle.save(article);

        Comment comment = Comment.builder()
                .body("firstComment")
                .author(user)
                .article(article)
                .build();
        underTestComment.save(comment);

        //when
        Comment expectedComment = underTestComment.getById(comment.getId());

        //then
        assertThat(expectedComment).isNotNull();
    }

    @Test
    void WillThrowWhenIdNotFound() {
        //given
        UUID randomId = UUID.randomUUID();

        //when
        Throwable thrown = catchThrowable(() -> {
            underTestComment.getById(randomId);
        });

        //then
        assertThat(thrown).isInstanceOf(RecordNotFoundException.class);
    }
}