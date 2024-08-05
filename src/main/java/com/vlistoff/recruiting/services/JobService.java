package com.vlistoff.recruiting.services;

import com.vlistoff.recruiting.entity.Job;
import com.vlistoff.recruiting.enums.JobStatus;
import java.util.List;

public interface JobService {
    void createJob(Job job);

    void updateJob(Job updatedJob);

    void deleteJob(Long id);

    List<Job> findAllJobs();

    List<Job> findByStatus(JobStatus status);

    List<Job> findJobsByCreator(Long creatorId);

    Job findJobById(Long jobId);

    List<Job> searchJobs(String query);
}
