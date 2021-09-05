package com.assem.blog.dao;

import com.assem.blog.entity.Article;
import com.assem.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    public Article findByBody(String body);
}
