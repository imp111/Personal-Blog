package com.example.blog.controller.admin;

import com.example.blog.entity.User;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RoleRepository roleRepository;

    // List all users
    @GetMapping("/")
    public String listUsers(Model model)
    {
        List<User> users = this.userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/user/list");

        return "base-layout";
    }
}
