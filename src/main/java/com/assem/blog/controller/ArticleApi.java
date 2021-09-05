package com.assem.blog.controller;

import com.assem.blog.dto.ArticleDto;
import com.assem.blog.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/articles")
@Slf4j
@AllArgsConstructor
public class ArticleApi {
    private final ArticleService articleService;

    @GetMapping
    public List<ArticleDto> getArticles() {

        List<ArticleDto> articles = articleService.findAll();

        return articles;
    }

    @GetMapping("/{articleId}")
    public ArticleDto getArticle(@PathVariable UUID articleId) {

        return articleService.findById(articleId);
    }

    @PostMapping
    public ArticleDto create(@Valid @RequestBody ArticleDto articleDto) {

        return articleService.create(articleDto);
//        log.info(title, body , desc);
    }

    @PutMapping("/{articleId}")
    public ArticleDto update(@PathVariable UUID articleId, @Valid @RequestBody ArticleDto articleDto) {
        return articleService.update(articleId, articleDto);

//        return articleService.create(articleDto);
//        log.info(title, body , desc);
    }

    @DeleteMapping("/{articleId}")
    public void delete(@PathVariable UUID articleId) {
        articleService.delete(articleId);
    }
}
