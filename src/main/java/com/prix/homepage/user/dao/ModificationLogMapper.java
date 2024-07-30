package com.prix.homepage.user.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.ModificationLog;

@Mapper
@Repository
public interface ModificationLogMapper {
  List<ModificationLog> findAll();

  void insertModLog(@Param("modDate") Date modDate, @Param("modVersion") String modVersion, @Param("modFile") String modFile);
}
