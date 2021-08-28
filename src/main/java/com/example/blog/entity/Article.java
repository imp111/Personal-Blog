package com.example.blog.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {
    private Integer id;
    private String title;
    private String content;
    private User author;
    private Category category;
    private Set<Tag> tags;

    // Tag
    @ManyToMany()
    @JoinColumn(table = "articles_tags")
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    // Category
    @ManyToOne()
    @JoinColumn(nullable = false, name = "categoryId")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    // Title
    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Content
    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Author
    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    // Summary
    @Transient
    public String getSummary()
    {
        return this.getContent().substring(0, this.getContent().length() / 2) + "...";
    }

    // Constructors
    public Article(String title, String content, User author, Category category, HashSet<Tag> tags)
    {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.tags = tags;
    }

    public Article(){ }
}
