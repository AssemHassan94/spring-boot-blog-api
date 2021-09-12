package com.assem.blog.service;

import com.assem.blog.dao.ArticleRepository;
import com.assem.blog.entity.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;
    private Article article;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(articleRepository);
        article = Article.builder()
                .title("Test Article")
                .body("Test Body")
                .desc("Short Article")
                .build();
    }

    @Test
    void CanFindAllArticles() {
        //when
        articleService.findAll();

        //then
        verify(articleRepository).findAll();
    }

    @Test
    void CanFindArticleById() {
        //given
        when(articleRepository.getById(article.getId())).thenReturn(article);

        //when
        articleService.findById(article.getId());

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);
        verify(articleRepository)
                .getById(uuidArgumentCaptor.capture());
        //then
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(article.getId());
    }

    @Test
    void CanCreateNewArticle() {
        //given
        when(articleRepository.save(ArgumentMatchers.any(Article.class))).thenReturn(article);

        //when
        articleService.create(article.asDTO());

        //then
        ArgumentCaptor<Article> articleArgumentCaptor =
                ArgumentCaptor.forClass(Article.class);

        verify(articleRepository)
                .save(articleArgumentCaptor.capture());

        Article capturedArticle = articleArgumentCaptor.getValue();

        assertThat(capturedArticle.getTitle()).isEqualTo(article.getTitle());
        assertThat(capturedArticle.getBody()).isEqualTo(article.getBody());
        assertThat(capturedArticle.getDesc()).isEqualTo(article.getDesc());
    }

    @Test
    void CanUpdateExistedArticle() {

        //given
        when(articleRepository.getById(article.getId())).thenReturn(article);

        Article updatedArticle = Article.builder()
                .title("Updated Article")
                .body("Test Body")
                .desc("Short Article")
                .build();

        when(articleRepository.save(ArgumentMatchers.any(Article.class))).thenReturn(updatedArticle);

        //when
        articleService.update(article.getId(), article.asDTO());

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);

        verify(articleRepository)
                .getById(uuidArgumentCaptor.capture());

        ArgumentCaptor<Article> articleArgumentCaptor =
                ArgumentCaptor.forClass(Article.class);

        verify(articleRepository)
                .save(articleArgumentCaptor.capture());
        Article capturedArticle = articleArgumentCaptor.getValue();

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(article.getId());

        assertThat(capturedArticle.getTitle()).isNotEqualTo(updatedArticle.getTitle());
        assertThat(capturedArticle.getBody()).isEqualTo(updatedArticle.getBody());
        assertThat(capturedArticle.getDesc()).isEqualTo(updatedArticle.getDesc());
    }

    @Test
    void CanDeleteExistedArticle() {
        //given
        UUID articleId = article.getId();

        //when
        articleService.delete(articleId);

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);

        verify(articleRepository)
                .getById(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(articleId);
    }
}