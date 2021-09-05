package com.assem.blog.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
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


}
