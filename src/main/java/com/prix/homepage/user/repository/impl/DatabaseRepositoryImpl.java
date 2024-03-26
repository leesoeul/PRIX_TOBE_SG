package com.prix.homepage.user.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prix.homepage.user.domain.Database;
import com.prix.homepage.user.mapper.DatabaseMapper;
import com.prix.homepage.user.repository.DatabaseRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DatabaseRepositoryImpl implements DatabaseRepository{

  private final DatabaseMapper databaseMapper;

  @Override
  public List<Database> getAllDatabase() {
    return databaseMapper.getAllDatabase();
  }
  
}
