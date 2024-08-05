package com.vlistoff.recruiting.controllers;

import com.vlistoff.recruiting.entity.User;
import com.vlistoff.recruiting.enums.UserRole;
import com.vlistoff.recruiting.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserServiceImpl userService;

    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public ModelAndView register(User user, @RequestParam("role") String role) {
        user.setRole(UserRole.valueOf(role));
        userService.registerUser(user);
        return new ModelAndView("redirect:/auth/login");
    }
}
