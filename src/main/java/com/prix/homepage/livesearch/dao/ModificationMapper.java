package com.prix.homepage.livesearch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.pojo.Modification;

@Mapper
@Repository
public interface ModificationMapper {
  
  //var_ptms_list에서 사용
  List<Modification> findModListByUserMod(Integer userId, Boolean variable, Boolean engine, String sortBy);

  void insert(Integer userId, String name, double massDiff, String residue, String position);

  void deleteById(Integer id);

  List<Modification> selectModListNotInUserMod(Integer userId, String variable, Boolean engine);
  
  List<Modification> selectModJoinClass(Integer userId, Boolean variable, Integer engine, String filter, String sortBy);

  Double selectMassDiffByName(String name);

  List<Modification> selectFixedModInUserMod(Integer id, Integer engine);

  List<Modification> selectVarModInUserMod(Integer id, Integer engine);
  
}
