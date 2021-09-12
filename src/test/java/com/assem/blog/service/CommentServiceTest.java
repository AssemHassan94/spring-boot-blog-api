package com.assem.blog.service;

import com.assem.blog.dao.ArticleRepository;
import com.assem.blog.dao.CommentRepository;
import com.assem.blog.entity.Article;
import com.assem.blog.entity.Comment;
import com.assem.blog.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private Article article;
    private User user;
    private Comment comment;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, articleRepository);

        user = User.builder()
                .userName("assem")
                .password("123456")
                .bio("post writer")
                .build();

        article = Article.builder()
                .title("Test Article")
                .body("Test Body")
                .desc("Short Article")
                .build();

        comment = Comment.builder()
                .body("Test Comment")
                .article(article)
                .author(user)
                .build();

    }

    @Test
    void CanFindAllComments() {
        //when
        commentService.findAll();

        //then
        verify(commentRepository).findAll();
    }

    @Test
    void CanFindExistedCommentById() {
        //given
        when(commentRepository.getById(comment.getId())).thenReturn(comment);
        //when
        commentService.findById(comment.getId());

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);
        verify(commentRepository)
                .getById(uuidArgumentCaptor.capture());
        //then
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(comment.getId());
    }

    @Test
    void CanCreateNewComment() {
        //given
        Set<Comment> comments = new HashSet<>();
        comments.add(comment);
        Article currentArticle = Article.builder()
                .title("Test Article")
                .body("Test Body")
                .desc("Short Article")
                .comments(comments)
                .build();
        when(articleRepository.getById(currentArticle.getId())).thenReturn(currentArticle);
        when(articleRepository.save(ArgumentMatchers.any(Article.class))).thenReturn(currentArticle);
        when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(comment);

        //when
        commentService.create(currentArticle.getId(), comment.asDTO());

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);
        verify(articleRepository)
                .getById(uuidArgumentCaptor.capture());


        ArgumentCaptor<Comment> commentArgumentCaptor =
                ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository)
                .save(commentArgumentCaptor.capture());
        Comment capturedComment = commentArgumentCaptor.getValue();

        assertThat(capturedComment.getBody()).isEqualTo(comment.getBody());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(currentArticle.getId());
    }

    @Test
    void CanUpdateExistedComment() {
        //given
        when(commentRepository.getById(comment.getId())).thenReturn(comment);

        Comment updatedComment = Comment.builder().body("updatedComment").article(article).author(user).build();

        when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(updatedComment);

        //when
        commentService.update(comment.getId(), comment.asDTO());

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);

        verify(commentRepository)
                .getById(uuidArgumentCaptor.capture());

        ArgumentCaptor<Comment> commentArgumentCaptor =
                ArgumentCaptor.forClass(Comment.class);

        verify(commentRepository)
                .save(commentArgumentCaptor.capture());
        Comment capturedComment = commentArgumentCaptor.getValue();

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(comment.getId());

        assertThat(capturedComment.getBody()).isNotEqualTo(updatedComment.getBody());

    }

    @Test
    void CanDeleteExistedComment() {
        //given
        UUID commentId = comment.getId();

        //when
        commentService.delete(commentId);

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);

        verify(commentRepository)
                .getById(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(commentId);
    }
}