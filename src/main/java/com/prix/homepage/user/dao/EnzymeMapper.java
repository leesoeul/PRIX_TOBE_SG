package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.Enzyme;

@Mapper
@Repository
public interface EnzymeMapper {
  List<Enzyme> findByUserId(Integer userId);

  Integer selectIdByUserIdAndName(Integer userId, String name);

  void insertEnzyme(Integer userId, String name, String ntCleave, String ctCleave);
  
  void deleteEnzyme(Integer id, Integer userId);

  void updateEnzyme(Enzyme enzyme);
}
