package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.Database;


@Mapper
@Repository
public interface DatabaseMapper {
  List<Database> findAll();

  Database selectById(Integer id);

  Integer selectIdByDataId(Integer dataId);
} 