package com.prix.homepage.livesearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.entity.UserSetting;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Integer>{
  

}
