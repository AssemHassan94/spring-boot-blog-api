package com.assem.blog.dao;

import com.assem.blog.entity.Comment;

import com.assem.blog.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @NonNull
    default Comment getById(@NonNull UUID commentId) {
        return findById(commentId).orElseThrow(RecordNotFoundException::new);
    }
}
