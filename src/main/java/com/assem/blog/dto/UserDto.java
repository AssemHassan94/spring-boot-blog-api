package com.assem.blog.dto;

import com.assem.blog.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class UserDto {

    private UUID id;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String bio;
    
}
