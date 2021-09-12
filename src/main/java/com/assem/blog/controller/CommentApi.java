package com.assem.blog.controller;


import com.assem.blog.dto.CommentDto;
import com.assem.blog.entity.Comment;
import com.assem.blog.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    @GetMapping("/articles/{articleId}/comments")
    public List<CommentDto> getComments() {

        return commentService.findAll();
    }


    @PostMapping("/articles/{articleId}/comments")
    public CommentDto create(@PathVariable UUID articleId, @Valid @RequestBody CommentDto commentDto) {

        return commentService.create(articleId, commentDto);
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public CommentDto update(@PathVariable UUID commentId, @Valid @RequestBody CommentDto commentDto) {
        return commentService.update(commentId, commentDto);
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public void delete(@PathVariable UUID commentId) {
        commentService.delete(commentId);
    }

}
