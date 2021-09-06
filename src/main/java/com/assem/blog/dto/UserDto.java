package com.assem.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDto {

    private UUID id;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String bio;
}
