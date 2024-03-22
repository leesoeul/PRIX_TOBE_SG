package com.prix.homepage.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prix.homepage.user.dao.ModificationLogDAO;
import com.prix.homepage.user.entity.ModificationLog;
import com.prix.homepage.user.repository.ModificationLogRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Component
public class ModificationLogDAOImpl implements ModificationLogDAO{

  ModificationLogRepository modificationLogRepository;

  @Override
  public List<ModificationLog> getAllModificationLog() {
    return modificationLogRepository.findAll();
  }
  
}
