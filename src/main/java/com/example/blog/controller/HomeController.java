package com.example.blog.controller;

import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String index(Model model)
    {
        List<Article> articles = this.articleRepository.findAll();

        model.addAttribute("view", "home/index");
        model.addAttribute("articles", articles);

        return "base-layout";
    }

    // Redirect to 403.html if we try to access /admin from user account
    @RequestMapping("/error/403")
    public String accessDenied(Model model)
    {
        model.addAttribute("view", "error/403");

        return "base-layout";
    }
}
