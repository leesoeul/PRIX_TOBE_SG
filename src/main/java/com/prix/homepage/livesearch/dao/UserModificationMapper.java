package com.prix.homepage.livesearch.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserModificationMapper {

  void deleteByUserId(@Param("userId") Integer userId);

  void deleteByUserIdVar0(@Param("userId") String userId);

  Integer countModifications(@Param("userId") Integer userId, @Param("variable") Boolean variable, @Param("engine") Boolean engine);

  void deleteByUserIdAndModId(@Param("userId") Integer userId, Boolean engine, String[] modIds);

  void insertWithModIds(@Param("userId") Integer userId, String[] modIds, Boolean var, Boolean engine);

}
