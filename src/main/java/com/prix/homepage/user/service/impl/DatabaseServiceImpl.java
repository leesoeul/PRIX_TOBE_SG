package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.domain.Database;
import com.prix.homepage.user.repository.DatabaseRepository;
import com.prix.homepage.user.service.DatabaseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DatabaseServiceImpl implements DatabaseService{

  
  private final DatabaseRepository databaseRepository;

  @Override
  public List<Database> getAllDatabase() {
    List<Database> databases = databaseRepository.getAllDatabase();

    List<Database> listDatabase = new ArrayList<>();

    for(Database database : databases){
      listDatabase.add(
        Database.builder()
            .id(database.getId())
            .name(database.getName())
            .file(database.getName())
            .data_id(database.getData_id())
            .build()
      );
    }

    return listDatabase;
  }
}
