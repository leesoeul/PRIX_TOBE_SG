package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.pojo.Database;


public interface DatabaseService {

  List<Database>  getAllDatabase();

  void deleteDatabase(Integer id);

  void updateDatabase(Integer id, String name);

  void insertDatabase(String dbName, String dbPath, Integer index);
  
}
