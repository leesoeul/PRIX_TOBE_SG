package com.prix.homepage.constants.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prix.homepage.constants.entity.Modification;

public interface ModificationRepository extends JpaRepository<Modification, Integer> {
  
}
