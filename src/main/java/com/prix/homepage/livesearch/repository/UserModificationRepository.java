package com.prix.homepage.livesearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.entity.UserModification;
import com.prix.homepage.livesearch.entity.id.UserModificationId;

@Repository
public interface UserModificationRepository extends JpaRepository<UserModification, UserModificationId>{
  

}
