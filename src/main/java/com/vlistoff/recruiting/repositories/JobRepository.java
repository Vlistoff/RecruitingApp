package com.vlistoff.recruiting.repositories;

import com.vlistoff.recruiting.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vlistoff.recruiting.entity.Job;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // Методы для поиска вакансий по названию, статусу и другим параметрам
    List<Job> findByCreator_Id(Long creatorId);
    List<Job> findByStatus(JobStatus status);
    List<Job> findByRequiredSkillsContaining(String skill);
    List<Job> findByTitleContaining(String title);
}
