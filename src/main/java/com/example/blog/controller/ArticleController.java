package com.example.blog.controller;

import com.example.blog.bindingModel.ArticleBindingModel;
import com.example.blog.entity.Article;
import com.example.blog.entity.User;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    // Create Article
    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model)
    {
        model.addAttribute("view", "article/create");

        return "base-layout";
    }

    // Create Process
    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel)
    {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        Article articleEntity = new Article(
                articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity
        );

        this.articleRepository.saveAndFlush(articleEntity);

        return "redirect:/";
    }

    //Show full content of article
    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id)
    {
        if (!this.articleRepository.existsById(id))
        {
            return "redirect:/";
        }

        Article article = this.articleRepository.findById(id).orElse(null);

        model.addAttribute("article", article);
        model.addAttribute("view", "article/details");

        return "base-layout";
    }

    //Edit button, used to edit the article
    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model)
    {
        if(!this.articleRepository.existsById(id))
        {
            return "redirect:/";
        }

        Article article = this.articleRepository.findById(id).orElse(null);

        model.addAttribute("view", "article/edit");
        model.addAttribute("article", article);

        return "base-layout";
    }
}
