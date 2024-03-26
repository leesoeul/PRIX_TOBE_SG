package com.prix.homepage.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.prix.homepage.user.domain.ModificationLog;

@Mapper
public interface ModificationLogMapper {
  List<ModificationLog> findAll();
}
