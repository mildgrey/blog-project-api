package com.tal.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tal.blog.entity.User;
import com.tal.blog.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    @Autowired
    private UserService userService;
    @ModelAttribute("user")
    public User user() {
        return new User();
    }
    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/registration?success";
    }
}