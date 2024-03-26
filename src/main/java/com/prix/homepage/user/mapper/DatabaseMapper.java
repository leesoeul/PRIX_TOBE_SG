package com.prix.homepage.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.prix.homepage.user.domain.Database;


@Mapper
public interface DatabaseMapper {
  List<Database> getAllDatabase();
} 