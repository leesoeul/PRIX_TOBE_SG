package com.prix.homepage.livesearch.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SearchLogMapper {
  void insert(
      Integer userId,
      String title,
      Integer msFile,
      Integer db,
      Integer result,
      String engine);

  Integer findUserId(Integer userId, Integer result);
}
