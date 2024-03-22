package com.prix.homepage.constants.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prix.homepage.constants.entity.Classification;

public interface ClassficationRepository extends JpaRepository<Classification, Integer> {
  
}
