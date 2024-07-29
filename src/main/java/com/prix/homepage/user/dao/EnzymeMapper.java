package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.Enzyme;

@Mapper
@Repository
public interface EnzymeMapper {
  List<Enzyme> findByUserId(Integer userId);

  Integer selectIdByUserIdAndName(Integer userId, String name);

  void insertEnzyme(@Param("userId") Integer userId, @Param("name") String name, @Param("ntCleave") String ntCleave, @Param("ctCleave") String ctCleave);
  
  void deleteEnzyme(@Param("id") Integer id, @Param("userId") Integer userId);

  Enzyme selectById(Integer id);

  void updateEnzyme(Enzyme enzyme);
}
