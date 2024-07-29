package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.Classification;


@Mapper
@Repository
public interface ClassificationMapper {
  
  Integer selectByClass(String nodeName);

  Integer selectMax();

  void insertNew(String nodeName);

} 