package com.assem.blog.service;

import com.assem.blog.dao.ArticleRepository;
import com.assem.blog.dao.CommentRepository;
import com.assem.blog.dto.ArticleDto;
import com.assem.blog.dto.CommentDto;
import com.assem.blog.entity.Article;
import com.assem.blog.entity.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<CommentDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments
        ) {
            commentDtos.add(comment.asDTO());
        }
//        return articleRepository.findAll().stream().map(Article::asDTO).collect(Collectors.toList());
        return commentDtos;
    }

    public CommentDto findById(UUID commentId) {
        return commentRepository.findById(commentId).get().asDTO();
        
    }

    public CommentDto create(UUID articleId, CommentDto commentDto) {

        Article article = articleRepository.getById(articleId);
        Comment comment = Comment.builder().body(commentDto.getBody()).build();

        article.addComment(comment);
        articleRepository.save(article);

        return commentRepository.save(comment).asDTO();
    }


    public CommentDto update(UUID commentId, CommentDto commentDto) {

        Comment comment = commentRepository.getById(commentId);
        comment.update(commentDto.getBody());

        return commentRepository.save(comment).asDTO();
    }

    public void delete(UUID commentId) {

        commentRepository.delete(commentRepository.getById(commentId));

    }
}
