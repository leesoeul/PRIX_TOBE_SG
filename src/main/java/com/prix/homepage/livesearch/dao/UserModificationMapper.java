package com.prix.homepage.livesearch.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserModificationMapper {
  
  public void deleteByUserId(Integer userId);

  public void deleteByUserIdVar0(String userId);
  
}
