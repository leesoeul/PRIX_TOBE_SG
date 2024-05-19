package com.prix.homepage.livesearch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.dao.ModificationMapper;
import com.prix.homepage.livesearch.pojo.Modification;
import com.prix.homepage.livesearch.service.ModificationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ModificationServiceImpl implements ModificationService {

  private final ModificationMapper modificationMapper;

  @Override
  public List<Modification> findModListByUserMod(Integer userId, Boolean variable, Boolean engine, String sortBy) {
    return modificationMapper.findModListByUserMod(userId, variable, engine, sortBy);
  }

  @Override
  public void insert(Integer userId, String name, double massDiff, String residue, String position) throws Exception {
    modificationMapper.insert(userId, name, massDiff, residue, position);
  }

  @Override
  public void deleteById(Integer id) throws Exception {
    modificationMapper.deleteById(id);
  }

  @Override
  public List<Modification> selectModListNotInUserMod(Integer userId, String var, Boolean engine) {
    return modificationMapper.selectModListNotInUserMod(userId, var, engine);
  }

  @Override
  public List<Modification> selectModJoinClass(Integer userId, Boolean variable, Integer engine, String filter,
      String sortBy) {
      return modificationMapper.selectModJoinClass(userId, variable, engine, filter, sortBy);
  }
  
  
}
