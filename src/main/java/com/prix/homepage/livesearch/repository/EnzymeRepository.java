package com.prix.homepage.livesearch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.entity.Enzyme;

@Repository
public interface EnzymeRepository extends JpaRepository<Enzyme, Integer>{
  
  @Query("SELECT e FROM Enzyme e WHERE e.user_id = ?1")
  List<Enzyme> findAllByUserId(Integer userId);

}
