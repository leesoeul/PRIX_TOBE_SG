package com.prix.homepage.download.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prix.homepage.download.entity.SoftwareRequest;

@Repository
public interface SoftwareRequestRepository extends JpaRepository<SoftwareRequest, Integer>{
  

}
