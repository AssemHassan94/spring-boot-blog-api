package com.assem.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@AllArgsConstructor
@Getter
public class RoleDto {

    private UUID id;

    @NotEmpty
    @NotNull
    private String Name;
}
