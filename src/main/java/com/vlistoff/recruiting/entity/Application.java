package com.vlistoff.recruiting.entity;


import com.vlistoff.recruiting.enums.ApplicationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Enumerated(EnumType.STRING) // Хранить enum как строку
    private ApplicationStatus appStatus; // "рассматривается", "приглашен на собеседование", "отклонен"

    private
    LocalDateTime applicationDate;

    // Конструкторы, геттеры и сеттеры
    public Application() {
    }

    public Application(User user, Job job, ApplicationStatus appStatus,
                       LocalDateTime applicationDate) {
        this.user = user;
        this.job = job;
        this.appStatus = appStatus;
        this.applicationDate = applicationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public
    LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(
            LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(ApplicationStatus appStatus) {
        this.appStatus = appStatus;
    }
}
