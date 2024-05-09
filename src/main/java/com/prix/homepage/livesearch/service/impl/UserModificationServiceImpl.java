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
  
}
