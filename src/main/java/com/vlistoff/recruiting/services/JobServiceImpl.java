package com.vlistoff.recruiting.services;

import com.vlistoff.recruiting.entity.Job;
import com.vlistoff.recruiting.enums.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vlistoff.recruiting.repositories.JobRepository;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void createJob(Job job) {
        jobRepository.save(job);
    }

    public void updateJob(Job updatedJob) {
        Job job = jobRepository.findById(updatedJob.getId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + updatedJob.getId()));

        if (updatedJob.getStatus() != null) {
            job.setStatus(updatedJob.getStatus());
        }
        if (updatedJob.getTitle() != null) {
            job.setTitle(updatedJob.getTitle());
        }
        if (updatedJob.getDescription() != null) {
            job.setDescription(updatedJob.getDescription());
        }
        if (updatedJob.getRequiredSkills() != null) {
            job.setRequiredSkills(updatedJob.getRequiredSkills());
        }
        if (updatedJob.getSalaryRange() != null) {
            job.setSalaryRange(updatedJob.getSalaryRange());
        }

        jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> findByStatus(JobStatus status) {
        return jobRepository.findByStatus(status);
    }

    public List<Job> findJobsByCreator(Long creatorId) {
        return jobRepository.findByCreator_Id(creatorId);
    }

    public Job findJobById(Long jobId) {
        if (jobId == null) {
            throw new IllegalArgumentException("Job ID must not be null");
        }
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new NoSuchElementException("No job found with id: " + jobId));
    }

    public List<Job> searchJobs(String query) {
        return jobRepository.findByTitleContaining(query);
    }
}

