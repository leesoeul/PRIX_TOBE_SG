package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.Database;


@Mapper
@Repository
public interface DatabaseMapper {
  List<Database> findAll();
  
  Database selectById(Integer id);

  Integer selectIdByDataId(Integer dataId);

  void insertDatabase(@Param("dbName") String dbName, @Param("dbPath") String dbPath, @Param("index") Integer index);

  void deleteById(Integer id);

  void update(Database database);

} 