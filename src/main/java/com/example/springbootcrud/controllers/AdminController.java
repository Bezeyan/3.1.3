package com.example.springbootcrud.controllers;

import com.example.springbootcrud.service.UserService;
import com.example.springbootcrud.service.UserServiceImpl;
import com.example.springbootcrud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;

    }
    @GetMapping({"/admin/users"})
    public String showAllUsers(Model model,  Authentication authentication) {
        model.addAttribute("user",authentication.getPrincipal());
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
}
