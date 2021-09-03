package com.example.springbootcrud.controllers;

import com.example.springbootcrud.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    private final UserServiceImpl userService;

    @Autowired
    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping({"/"})
    public String getLoginForm() {
        return "login";
    }

}
