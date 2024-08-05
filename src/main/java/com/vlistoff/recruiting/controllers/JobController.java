package com.vlistoff.recruiting.controllers;

import com.vlistoff.recruiting.entity.Application;
import com.vlistoff.recruiting.entity.Job;
import com.vlistoff.recruiting.entity.User;
import com.vlistoff.recruiting.enums.ApplicationStatus;
import com.vlistoff.recruiting.enums.JobStatus;
import com.vlistoff.recruiting.services.ApplicationServiceImpl;
import com.vlistoff.recruiting.services.JobServiceImpl;
import com.vlistoff.recruiting.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {
    private final JobServiceImpl jobService;
    private final UserServiceImpl userService;
    private final ApplicationServiceImpl applicationService;

    @Autowired
    public JobController(JobServiceImpl jobService, UserServiceImpl userService, ApplicationServiceImpl applicationService) {
        this.jobService = jobService;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("job", new Job());
        return "jobs/createJob";
    }

    @PostMapping("/create")
    public String createJob(@ModelAttribute Job job, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        job.setCreator(user);
        job.setStatus(JobStatus.OPEN);
        jobService.createJob(job);
        model.addAttribute("successMessage", "Job created successfully!");
        return "jobs/createJob";
    }

    @GetMapping("/list")
    public String listJobs(Model model) {
        List<Job> jobs = jobService.findByStatus(JobStatus.OPEN);
        model.addAttribute("jobs", jobs);
        return "jobs/listJobs";
    }

    @GetMapping("/myjobs")
    public String listUserJobs(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Получаем UserDetails
        String username = userDetails.getUsername(); // Получаем username

        // Используем username для поиска пользователя в базе данных
        User currentUser = userService.findByUsername(username);
        List<Job> jobs = jobService.findJobsByCreator(currentUser.getId());
        model.addAttribute("jobs", jobs);
        return "jobs/listUserJobs";
    }

    @GetMapping("/edit/{jobId}")
    public String showEditForm(@PathVariable Long jobId, Model model) {
        Job job = jobService.findJobById(jobId);
        model.addAttribute("job", job);
        return "jobs/editJob";
    }


    @PostMapping("/edit/{jobId}")
    public String updateJob(@PathVariable Long jobId, @ModelAttribute Job job, RedirectAttributes redirectAttributes) {
        Job existingJob = jobService.findJobById(jobId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Имя текущего пользователя

        if (!existingJob.getCreator().getUsername().equals(currentUsername)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to update this job.");
            return "redirect:/jobs/edit/" + jobId; // Редирект обратно на форму редактирования
        }

        jobService.updateJob(job);
        redirectAttributes.addFlashAttribute("successMessage", "Job updated successfully!");
        return "redirect:/jobs/edit/" + jobId; // Редирект обратно на форму редактирования
    }

    @GetMapping("/view/{jobId}")
    public String viewJob(@PathVariable Long jobId, Model model) {
        Job job = jobService.findJobById(jobId);
        model.addAttribute("job", job);
        return "jobs/jobDetails";
    }

    @PostMapping("/apply/{jobId}")
    @PreAuthorize("hasAuthority('CANDIDATE')")
    public String applyForJob(@PathVariable Long jobId, @AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirectAttributes) {
        Job job = jobService.findJobById(jobId);
        User candidate = userService.findByUsername(currentUser.getUsername());

        Application application = new Application();
        application.setJob(job);
        application.setUser(candidate);
        application.setAppStatus(ApplicationStatus.PENDING);
        application.setApplicationDate(LocalDateTime.now());
        applicationService.createApplication(application);

        redirectAttributes.addFlashAttribute("successMessage", "You have successfully applied for the job!");
        return "redirect:/jobs/view/" + jobId;
    }

    @GetMapping("/search")
    public String searchJobs(@RequestParam String query, Model model) {
        List<Job> searchResults = jobService.searchJobs(query);
        model.addAttribute("jobs", searchResults);
        return "jobs/jobSearch"; // Вернуть страницу с результатами поиска
    }

}

