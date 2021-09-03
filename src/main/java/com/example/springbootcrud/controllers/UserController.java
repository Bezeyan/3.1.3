package com.example.springbootcrud.controllers;

import com.example.springbootcrud.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/lk")
    public String getUserPage2(Model model, Principal principal, Authentication authentication) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("user",authentication.getPrincipal());
        return "userPage";
    }

}
