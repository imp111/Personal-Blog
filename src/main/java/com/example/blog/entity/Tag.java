package com.example.blog.entity;

import antlr.actions.python.CodeLexer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {
    private Integer id;
    private String name;
    private Set<Article> articles;

    // Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Name
    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Article
    @ManyToMany(mappedBy = "tags")
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    // Constructors
    public Tag(String name)
    {
        this.name = name;
        this.articles = new HashSet<>();
    }

    public Tag() { } // Empty constructor
}
