package com.prix.homepage.livesearch.service.impl;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.dao.UserModificationMapper;
import com.prix.homepage.livesearch.service.UserModificationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserModificationServiceImpl implements UserModificationService{

  private final UserModificationMapper userModificationMapper;

  @Override
  public void deleteByUserId(int userId) throws Exception{
    userModificationMapper.deleteByUserId(userId);
  }

  @Override
  public void deleteByUserIdVar0(String userId) throws Exception{
    userModificationMapper.deleteByUserIdVar0(userId);
  }

  @Override
  public Integer countModifications(Integer userId, Boolean engine) {
    return userModificationMapper.countModifications(userId, engine);
  }

  @Override
  public void deleteByUserIdAndModId(Integer userId, String engine, String[] modIds) throws Exception {
    userModificationMapper.deleteByUserIdAndModId(userId, engine, modIds);
  }

  @Override
  public void insertWithModIds(Integer userId, String[] modIds, Boolean var, Boolean engine) throws Exception {
    userModificationMapper.insertWithModIds(userId, modIds, var, engine);
  }
}
