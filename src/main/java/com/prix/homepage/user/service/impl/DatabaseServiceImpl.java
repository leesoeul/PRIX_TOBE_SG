package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.DatabaseDAO;
import com.prix.homepage.user.dto.databaseDto.DatabaseResponseDto;
import com.prix.homepage.user.entity.Database;
import com.prix.homepage.user.service.DatabaseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DatabaseServiceImpl implements DatabaseService{

  
  private final DatabaseDAO databaseDAO;

  @Override
  public List<DatabaseResponseDto> getAllDatabase() {
    List<Database> databases = databaseDAO.getAllDatabase();

    List<DatabaseResponseDto> listDatabaseResponseDto = new ArrayList<>();

    for(Database database : databases){
      listDatabaseResponseDto.add(
        DatabaseResponseDto.builder()
            .id(database.getId())
            .name(database.getName())
            .file(database.getName())
            .data_id(database.getData_id())
            .build()
      );
    }

    return listDatabaseResponseDto;
  }
}
