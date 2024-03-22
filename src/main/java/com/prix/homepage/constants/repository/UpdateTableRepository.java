package com.prix.homepage.constants.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prix.homepage.constants.entity.UpdateTable;

public interface UpdateTableRepository extends JpaRepository<UpdateTable, String> {
  
}
