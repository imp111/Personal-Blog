package com.example.blog.controller.admin;

import com.example.blog.bindingModel.CategoryBindingModel;
import com.example.blog.entity.Category;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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

        categories = categories.stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());

        model.addAttribute("view", "admin/category/list");
        model.addAttribute("categories", categories);

        return "base-layout";
    }

    // Returns only creation form
    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("view", "admin/category/create");

        return "base-layout";
    }

    // Create new category
    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel)
    {
        if (StringUtils.isEmpty(categoryBindingModel.getName()))
        {
            return "redirect:/admin/categories/create"; // "redirect:/admin/category/create";
        }

        Category category = new Category(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);

        return "redirect:/admin/categories/";
    }
}
