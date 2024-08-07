package com.prix.homepage.livesearch.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.pojo.Data;


@Mapper
@Repository
public interface DataMapper {

  String getNameById(Integer id);

  void insert(String type, String name, byte[] content);

  int getMaxId();

  void updateContent(Integer id, byte[] content);

  Byte[] findContentById(Integer id);

  Data getNameContentById(Integer id);
}
