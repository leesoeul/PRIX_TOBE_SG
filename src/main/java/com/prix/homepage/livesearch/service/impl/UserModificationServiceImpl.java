package com.prix.homepage.livesearch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.dao.ModificationMapper;
import com.prix.homepage.livesearch.dao.UserModificationMapper;
import com.prix.homepage.livesearch.pojo.Modification;
import com.prix.homepage.livesearch.service.UserModificationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserModificationServiceImpl implements UserModificationService{

  private final UserModificationMapper userModificationMapper;
  private final ModificationMapper modificationMapper;

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
  public List<Modification> findModListByUserMod(Integer userId, Integer variable, Integer engine, String sortBy) {
    return modificationMapper.findModListByUserMod(userId, variable, engine, sortBy);
  }
  
}
