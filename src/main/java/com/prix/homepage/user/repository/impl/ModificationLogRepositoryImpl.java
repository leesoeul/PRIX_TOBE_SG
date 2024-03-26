package com.prix.homepage.user.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prix.homepage.user.domain.ModificationLog;
import com.prix.homepage.user.mapper.ModificationLogMapper;
import com.prix.homepage.user.repository.ModificationLogRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ModificationLogRepositoryImpl implements ModificationLogRepository {

  private final ModificationLogMapper modificationLogMapper;

  @Override
  public List<ModificationLog> getAllModificationLog() {
    return modificationLogMapper.findAll();
  }
  
}
