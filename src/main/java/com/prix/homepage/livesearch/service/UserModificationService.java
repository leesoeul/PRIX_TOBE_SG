package com.prix.homepage.livesearch.service;

import java.util.List;

import com.prix.homepage.livesearch.pojo.Modification;

public interface UserModificationService {

  void deleteByUserId(int userId) throws Exception;

  void deleteByUserIdVar0(String userId) throws Exception;

  Integer countModifications(Integer userId, Boolean engine);

  void deleteByUserIdAndModId(Integer userId, String engine, String[] modIds) throws Exception;

  List<Modification> findModListByUserMod(Integer userId, Integer variable, Integer engine, String sortBy);
}
