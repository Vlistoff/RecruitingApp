package com.vlistoff.recruiting.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vlistoff.recruiting.entity.User;
import com.vlistoff.recruiting.services.UserServiceImpl;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserServiceImpl userService;

    @Autowired
    public ProfileController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getProfile(@AuthenticationPrincipal UserDetails currentUser, Model model) {
            User user = userService.findByUsername(currentUser.getUsername());
            model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/{candidateId}")
    public String getProfileCandidate(Model model, @PathVariable long candidateId) {
        User user = userService.findById(candidateId);
        model.addAttribute("user", user);

        return "user/viewprofile";
    }

    @PostMapping
    public String updateProfile(User user, Model model) {
        userService.updateUser(user);
        model.addAttribute("user", user);
        model.addAttribute("successMessage", "Profile updated successfully!");
        return "user/profile";
    }
}
