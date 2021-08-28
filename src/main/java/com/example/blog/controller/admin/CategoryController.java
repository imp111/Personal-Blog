package com.example.blog.controller.admin;

import com.example.blog.bindingModel.CategoryBindingModel;
import com.example.blog.entity.Article;
import com.example.blog.entity.Category;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // List all categories
    @GetMapping("/")
    public String list(Model model)
    {
        List<Category> categories = this.categoryRepository.findAll();

        categories = categories.stream().sorted(Comparator.comparingInt(Category::getId))
                        .collect(Collectors.toList());

        model.addAttribute("view", "admin/category/list");
        model.addAttribute("categories", categories);

        return "base-layout";
    }

    // Create new categories
    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("view", "admin/category/create");

        return "base-layout";
    }

    // This actually creates the category
    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel)
    {
        if (StringUtils.isEmpty(categoryBindingModel.getName()))
        {
            return "redirect:/admin/categories/create";
        }

        Category category = new Category(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);

        return "redirect:/admin/categories/";
    }

    // Edit category button functionality (in admin panel)
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id)
    {
        if (!this.categoryRepository.existsById(id))
        {
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findById(id).orElse(null);

        model.addAttribute("view", "admin/category/edit");
        model.addAttribute("category", category);

        return "base-layout";
    }

    // Applies the changes to the article
    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel)
    {
        if (!this.categoryRepository.existsById(id))
        {
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findById(id).orElse(null);

        category.setName(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);

        return "redirect:/admin/categories/";
    }

    // Opens delete article button
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Integer id)
    {
        if (!this.categoryRepository.existsById(id))
        {
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findById(id).orElse(null);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/delete");

        return "base-layout";
    }

    // This method actually deletes the category
    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id)
    {
        if (!this.categoryRepository.existsById(id))
        {
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findById(id).orElse(null);

        for (Article article : category.getArticles())
        {
            this.articleRepository.delete(article);
        }

        this.categoryRepository.delete(category);

        return "redirect:/admin/categories/";
    }
}
