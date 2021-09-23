package com.assem.blog.entity;

import com.assem.blog.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "bio")
    private String bio;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH
                    , CascadeType.REFRESH, CascadeType.MERGE}
            , mappedBy = "author")
    private List<Article> articles;

    @ManyToMany(fetch = FetchType.LAZY
            , cascade = {CascadeType.PERSIST, CascadeType.DETACH
            , CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "user_followings",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "following_id")})
    private Set<User> followings = new HashSet<>();

    @ManyToMany(mappedBy = "followings", fetch = FetchType.LAZY)
    private final Set<User> followers = new HashSet<>();


    @ManyToMany(mappedBy = "userFavorited")
    private Set<Article> favoritedArticles = new HashSet<>();


    public void createArticle(Article article) {
        this.articles.add(article);
        article.setAuthor(this);
    }

    public void removeArticle(Article article) {
        this.articles.remove(article);
        article.setAuthor(null);
    }


    public void followUser(User userToFollow) {
        followings.add(userToFollow);
    }

    public void unFollowUser(User userToUnFollow) {
        followings.remove(userToUnFollow);
    }

    public void favoriteArticle(Article articleToFavorite) {
        favoritedArticles.add(articleToFavorite);
    }

    public void unFavoriteArticle(Article unFavoriteArticle) {
        favoritedArticles.remove(unFavoriteArticle);
    }

    public void update(String userName, String password, String bio) {
        this.username = userName;
        this.password = password;
        this.bio = bio;
    }

    public UserDto asDTO() {
        return new UserDto(id, username, password, bio);
    }
}
