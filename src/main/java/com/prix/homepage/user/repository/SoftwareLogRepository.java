package com.prix.homepage.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.entity.SoftwareLog;

@Repository
public interface SoftwareLogRepository extends JpaRepository<SoftwareLog, Integer>{
  
  List<SoftwareLog> findAllByOrderByDateAscIdAsc();
}
