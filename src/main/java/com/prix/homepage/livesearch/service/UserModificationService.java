package com.prix.homepage.livesearch.service;

public interface UserModificationService {

  void deleteByUserId(int userId) throws Exception;

  void deleteByUserIdVar0(String userId) throws Exception;

  Integer countModifications(Integer userId, Boolean variable, Boolean engine);

  void deleteByUserIdAndModId(Integer userId, Boolean engine, String[] modIds) throws Exception;

  void insertWithModIds(Integer userId, String[] modIds, Boolean var, Boolean engine) throws Exception;

}
