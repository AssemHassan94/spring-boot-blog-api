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

    @Column(name = "email")
    private String email;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY)
    private List<Article> articles;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


    public User(String userName, String email, String bio, String image) {
        this.userName = userName;
        this.email = email;
        this.bio = bio;
        this.image = image;
    }

    public User() {
    }
    public void addArticle(Article tempArticle) {
        if (articles == null) {
            articles = new ArrayList<Article>();
        }
        articles.add(tempArticle);
        tempArticle.setAuthor(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserName().equals(user.getUserName()) && getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
                ", articles=" + articles +
                '}';
    }
}
