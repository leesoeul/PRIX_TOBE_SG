package com.prix.homepage.constants.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prix.homepage.constants.entity.UserEnzyme;
import com.prix.homepage.constants.entity.id.UserEnzymeId;

public interface UserEnzymeRepository extends JpaRepository<UserEnzyme, UserEnzymeId> {
  
}
