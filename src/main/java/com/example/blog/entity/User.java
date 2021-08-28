package com.example.blog.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    private Integer id;
    private String email;
    private String fullName;
    private String password;
    private Set<Role> roles;
    private Set<Article> aritcles;

    // Roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role)
    {
        this.roles.add(role);
    }

    // Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Email
    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Full name
    @Column(name = "fullName", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Password
    @Column(name = "password", length = 60, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Articles
    @OneToMany(mappedBy = "author")
    public Set<Article> getArticles() {
        return aritcles;
    }

    public void setArticles(Set<Article> articles) {
        this.aritcles = aritcles;
    }

    // Checks if the current user is admin or not
    @Transient
    public boolean isAdmin()
    {
        return this.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

    // Checks if the current user is the author of the article or not
    @Transient
    public boolean isAuthor(Article article)
    {
        return Objects.equals(this.getId(),
                article.getAuthor().getId());
    }

    // Constructors
    public User(String email, String fullName, String password)
    {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = new HashSet<>();
        this.aritcles = new HashSet<>();
    }

    public User(){ }


}
