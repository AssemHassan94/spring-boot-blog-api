package com.assem.blog.dao;

import com.assem.blog.entity.Article;
import com.assem.blog.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    default Article getById(UUID articleId) {
        return findById(articleId).orElseThrow(RecordNotFoundException::new);
    }

}
