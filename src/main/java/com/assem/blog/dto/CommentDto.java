package com.assem.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;


@AllArgsConstructor
@Getter
public class CommentDto {

    private UUID id;

    @NotEmpty
    private String body;
}
