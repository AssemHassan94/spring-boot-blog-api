package com.assem.blog.entity;

import com.assem.blog.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "bio")
    private String bio;

    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY)
    private List<Article> articles;

    public UserDto asDTO() {
        return new UserDto(id, userName, password, bio);
    }

    public void update(String userName, String password, String bio) {
        this.userName = userName;
        this.password = password;
        this.bio = bio;
    }


}
