package com.vlistoff.recruiting.repositories;

import com.vlistoff.recruiting.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vlistoff.recruiting.entity.Application;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Application findById(long id);
    List<Application> findByUserId(Long userId);
    List<Application> findByJobId(Long jobId);

    List<Application> findAllByUserId(Long userId);

    @Query("SELECT a FROM Application a WHERE a.job.creator.id = :creatorId")
    List<Application> findAllByJobCreatorId(Long creatorId);
}

