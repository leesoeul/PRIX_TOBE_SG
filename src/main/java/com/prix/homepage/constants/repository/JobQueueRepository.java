package com.prix.homepage.constants.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prix.homepage.constants.entity.JobQueue;

public interface JobQueueRepository extends JpaRepository<JobQueue, Integer> {
  
}
