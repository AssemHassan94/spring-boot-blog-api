package com.assem.blog.api;

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
public class ArticleResource {
    private final ArticleService articleService;

    @GetMapping
    public List<ArticleDto> getArticles() {

        return articleService.findAll();
    }

    @GetMapping("/{articleId}")
    public ArticleDto getArticle(@PathVariable UUID articleId) {
        return articleService.findById(articleId);
    }

    @PostMapping
    public ArticleDto create(@Valid @RequestBody ArticleDto articleDto) {

        return articleService.create(articleDto);
    }

    @PutMapping("/{articleId}")
    public ArticleDto update(@PathVariable UUID articleId, @Valid @RequestBody ArticleDto articleDto) {
        return articleService.update(articleId, articleDto);

    }

    @DeleteMapping("/{articleId}")
    public void delete(@PathVariable UUID articleId) {

        ArticleDto articleDto = articleService.findById(articleId);
        articleService.delete(articleId);
    }
}
