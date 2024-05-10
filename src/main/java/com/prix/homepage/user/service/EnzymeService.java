package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.pojo.Enzyme;


public interface EnzymeService {
  
  List<Enzyme> getAllEnzymeByUserId(Integer userId);

  Integer selectIdByUserIdAndName(Integer userId, String name);

  void insertEnzyme(Integer userId, String name, String ntCleave, String ctCleave) throws Exception;

  void deleteEnzyme(Integer id, Integer userId) throws Exception;
}
