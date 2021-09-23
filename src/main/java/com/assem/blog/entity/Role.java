package com.assem.blog.entity;

import com.assem.blog.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    public RoleDto asDto() {
        return new RoleDto(id, name);
    }
}
