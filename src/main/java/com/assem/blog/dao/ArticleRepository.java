package com.assem.blog.dao;

import com.assem.blog.entity.Article;
import com.assem.blog.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    @NonNull
    default Article getById(@NonNull UUID articleId) {
        return findById(articleId).orElseThrow(RecordNotFoundException::new);
    }

}
