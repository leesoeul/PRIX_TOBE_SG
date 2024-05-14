package com.prix.homepage.livesearch.service;

public interface UserModificationService {

  void deleteByUserId(int userId) throws Exception;

  void deleteByUserIdVar0(String userId) throws Exception;

  Integer countModifications(Integer userId, Boolean engine);

  void deleteByUserIdAndModId(Integer userId, String engine, String[] modIds) throws Exception;

  void insertWithModIds(Integer userId, String[] modIds, String var, String engine) throws Exception;

}
