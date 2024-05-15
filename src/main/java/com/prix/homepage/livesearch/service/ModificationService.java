package com.prix.homepage.livesearch.service;

import java.util.List;

import com.prix.homepage.livesearch.pojo.Modification;

public interface ModificationService {
  List<Modification> findModListByUserMod(Integer userId, Integer variable, Integer engine, String sortBy);

  void insert(Integer userId, String name, double massDiff, String residue, String position) throws Exception;
  
  void deleteById(Integer id) throws Exception;

  List<Modification> selectModListNotInUserMod(Integer userId, String var, Boolean engine);

  List<Modification> selectModJoinClass(Integer userId, String var, Integer engine, String filter, String sortBy);
}
