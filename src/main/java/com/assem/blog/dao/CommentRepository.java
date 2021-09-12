package com.assem.blog.dao;

import com.assem.blog.entity.Comment;

import com.assem.blog.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    default Comment getById(UUID commentId) {
        return findById(commentId).orElseThrow(RecordNotFoundException::new);
    }
}
