package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prix.homepage.user.dao.DatabaseMapper;
import com.prix.homepage.user.pojo.Database;
import com.prix.homepage.user.service.DatabaseService;

import lombok.AllArgsConstructor;

/**
 * Database객체와 관련된 서비스
 */
@AllArgsConstructor
@Service
public class DatabaseServiceImpl implements DatabaseService{

  
  private final DatabaseMapper databaseMapper;

  /**
   * db에서 px_database 테이블의 데이터를 모두 가져옴
   */
  @Override
  public List<Database> getAllDatabase() {
    List<Database> databases = databaseMapper.findAll();

    List<Database> listDatabase = new ArrayList<>();

    for(Database database : databases){
      listDatabase.add(
        Database.builder()
            .id(database.getId())
            .name(database.getName())
            .file(database.getName())
            .dataId(database.getDataId())
            .build()
      );
    }

    return listDatabase;
  }

    @Override
    @Transactional
    public void deleteDatabase(Integer id) {
        databaseMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void updateDatabase(Integer id, String name) {
        Database database = Database.builder()
            .id(id)
            .name(name)
            .build();
        databaseMapper.update(database);
    }
}
