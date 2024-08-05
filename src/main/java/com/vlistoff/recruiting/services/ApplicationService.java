package com.vlistoff.recruiting.services;

import com.vlistoff.recruiting.entity.Application;
import com.vlistoff.recruiting.enums.ApplicationStatus;

import java.util.List;

public interface ApplicationService {

    Application createApplication(Application application);

    Application updateApplicationStatus(Long applicationId, ApplicationStatus status);

    List<Application> findAllByUserId(Long userId);

    List<Application> getApplicationsForRecruiter(Long creatorId);

    void deleteApplication(Long applicationId);

    List<Application> findAllApplications();

    Application findById(Long applicationId);
}
