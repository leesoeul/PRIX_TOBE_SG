package com.prix.homepage.constants.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prix.homepage.constants.entity.SearchLog;

public interface SearchLogRepository extends JpaRepository<SearchLog, Integer> {
  
}
