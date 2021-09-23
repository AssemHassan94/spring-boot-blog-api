package com.assem.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@AllArgsConstructor
@Getter
public class RoleToUserDTO {


    @NotEmpty
    private String username;
    @NotEmpty
    @NotNull
    private String roleName;
}
