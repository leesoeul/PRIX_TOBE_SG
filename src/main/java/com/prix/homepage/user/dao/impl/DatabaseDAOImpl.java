package com.prix.homepage.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prix.homepage.user.dao.DatabaseDAO;
import com.prix.homepage.user.entity.Database;
import com.prix.homepage.user.repository.DatabaseRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DatabaseDAOImpl implements DatabaseDAO {

  DatabaseRepository databaseRepository;
  
  @Override
  public List<Database> getAllDatabase() {
      return databaseRepository.findAll();
  }
  
}
