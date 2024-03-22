package com.prix.homepage.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.entity.ModificationLog;

@Repository
public interface ModificationLogRepository extends JpaRepository<ModificationLog, Integer>{
  

}
