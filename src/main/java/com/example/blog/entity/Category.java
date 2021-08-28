package com.example.blog.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "categories")
public class Category {
    private Integer id;
    private String name;
    private Set<Article> articles;

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Name
    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Articles
    @OneToMany(mappedBy = "category")
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    // Constructors
    public Category(String name)
    {
        this.name = name;
        this.articles = new HashSet<>();
    }

    public Category() { }
}
