package com.prix.homepage.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.entity.SoftwareMsg;

@Repository
public interface SoftwareMsgRepository extends JpaRepository<SoftwareMsg, String>{

    @Query("SELECT sm FROM SoftwareMsg sm WHERE sm.id = ?1")
    List<SoftwareMsg> findAllById(String id);
  
}
