package com.assem.blog.service;

import com.assem.blog.dao.ArticleRepository;
import com.assem.blog.dto.ArticleDto;
import com.assem.blog.entity.Article;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleDto> findAll() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articles
        ) {
            articleDtos.add(article.asDTO());
        }
//        return articleRepository.findAll().stream().map(Article::asDTO).collect(Collectors.toList());
        return articleDtos;
    }


    public ArticleDto findById(UUID articleId) {
        return articleRepository.findById(articleId).get().asDTO();
    }

    public ArticleDto create(ArticleDto articleDto) {
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .body(articleDto.getBody())
                .desc(articleDto.getDesc())
                .build();

        return articleRepository.save(article).asDTO();

    }

    public ArticleDto update(UUID articleId, ArticleDto articleDto) {
        Article article = articleRepository.getById(articleId);
        article.update(articleDto.getTitle(), articleDto.getBody(), articleDto.getDesc());

        return articleRepository.save(article).asDTO();
    }

    public void delete(UUID articleId) {

        articleRepository.delete(articleRepository.getById(articleId));

    }
}
