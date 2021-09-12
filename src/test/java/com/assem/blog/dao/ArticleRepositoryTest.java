package com.assem.blog.dao;


import com.assem.blog.entity.Article;
import com.assem.blog.entity.User;
import com.assem.blog.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository underTestArticle;
    @Autowired
    private UserRepository underTestUser;

    @Test
    void CheckIfArticleExistsId() {
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

        //when
        Article expectedArticle = underTestArticle.getById(article.getId());

        //then
        assertThat(expectedArticle).isNotNull();
    }

    @Test
    void WillThrowWhenIdNotFound() {
        //given
        UUID randomId = UUID.randomUUID();

        //when
        Throwable thrown = catchThrowable(() -> {
            underTestArticle.getById(randomId);
        });

        //then
        assertThat(thrown).isInstanceOf(RecordNotFoundException.class);
    }
}