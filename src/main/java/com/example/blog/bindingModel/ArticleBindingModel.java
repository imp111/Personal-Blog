package com.example.blog.bindingModel;

import com.sun.istack.NotNull;

public class ArticleBindingModel {
    @NotNull
    private String title;

    @NotNull
    private String content;

    private Integer categoryId;
    private String tagString;

    // Tag String
    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    // Category Id
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    // Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
