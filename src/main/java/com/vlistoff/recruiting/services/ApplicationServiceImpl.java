package com.vlistoff.recruiting.services;

import com.vlistoff.recruiting.entity.Application;
import com.vlistoff.recruiting.enums.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vlistoff.recruiting.repositories.ApplicationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService{
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Application createApplication(Application application) {
        applicationRepository.save(application);
        return application;
    }

    @Transactional
    public Application updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));
        application.setAppStatus(status);
        return applicationRepository.save(application);
    }

    public List<Application> findAllByUserId(Long userId) {
        return applicationRepository.findAllByUserId(userId);
    }

    public List<Application> getApplicationsForRecruiter(Long creatorId) {
        return applicationRepository.findAllByJobCreatorId(creatorId);
    }

    public void deleteApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }

    public List<Application> findAllApplications() {
        return applicationRepository.findAll();
    }

    public Application findById(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));
    }
}

