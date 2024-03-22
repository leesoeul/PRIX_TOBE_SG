package com.prix.homepage.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prix.homepage.user.entity.Database;

public interface DatabaseRepository extends JpaRepository<Database, Integer> {
  
}
