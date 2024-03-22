package com.prix.homepage.constants.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prix.homepage.constants.entity.Data;

public interface DataRepository extends JpaRepository<Data, Integer> {
  
}
