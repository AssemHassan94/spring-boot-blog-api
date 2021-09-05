package com.assem.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ArticleDto {


    private UUID id;
    @NotEmpty
    private String title;

    @NotEmpty
    private String body;

    @NotEmpty
    private String desc;
}
