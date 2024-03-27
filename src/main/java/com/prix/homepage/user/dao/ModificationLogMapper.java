package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.ModificationLog;

@Mapper
@Repository
public interface ModificationLogMapper {
  List<ModificationLog> findAll();
}
