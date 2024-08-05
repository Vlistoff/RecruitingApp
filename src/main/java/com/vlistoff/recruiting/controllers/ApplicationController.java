package com.vlistoff.recruiting.controllers;

import com.vlistoff.recruiting.entity.Application;
import com.vlistoff.recruiting.entity.User;
import com.vlistoff.recruiting.enums.ApplicationStatus;
import com.vlistoff.recruiting.services.UserServiceImpl;
import com.vlistoff.recruiting.services.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationServiceImpl applicationService;
    private final UserServiceImpl userService;

    @Autowired
    public ApplicationController(ApplicationServiceImpl applicationService, UserServiceImpl userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    @GetMapping("/my")
    public String viewMyApplications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        List<Application> applications = applicationService.findAllByUserId(user.getId());
        model.addAttribute("applications", applications);
        return "applications/myApplications";
    }

    @GetMapping("/manageApplication")
    public String viewRecruiterApplications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        List<Application> applications = applicationService.getApplicationsForRecruiter(user.getId());
        model.addAttribute("applications", applications);
        return "applications/manageApplications";
    }

    @PostMapping("/update/{applicationId}")
    public String updateApplicationStatus(@PathVariable Long applicationId, @RequestParam ApplicationStatus status, RedirectAttributes redirectAttributes) {
        try {
            Application updatedApplication = applicationService.updateApplicationStatus(applicationId, status);
            redirectAttributes.addFlashAttribute("successMessage", "Application status updated to " + updatedApplication.getAppStatus());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/applications/manageApplication";
    }
}

