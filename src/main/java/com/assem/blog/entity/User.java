package com.assem.blog.entity;

import com.assem.blog.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "bio")
    private String bio;

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


    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH
                    , CascadeType.REFRESH, CascadeType.MERGE}
            , mappedBy = "author")
    private List<Article> articles;

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
        this.userName = userName;
        this.password = password;
        this.bio = bio;
    }

    public UserDto asDTO() {
        return new UserDto(this.id, this.userName, this.password, this.bio);
    }
}
