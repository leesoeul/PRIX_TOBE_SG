package com.prix.homepage.livesearch.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserModificationMapper {

  void deleteByUserId(Integer userId);

  void deleteByUserIdVar0(String userId);

  Integer countModifications(Integer userId, Boolean engine);

  void deleteByUserIdAndModId(Integer userId, String engine, String[] modIds);

  void insertWithModIds(Integer userId, String[] modIds, String var, String engine);

}
