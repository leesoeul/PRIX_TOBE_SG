package com.prix.homepage.user.dao;

import com.prix.homepage.user.pojo.SearchLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchLogMapper {
    List<SearchLog> findAll();

    String findFile(Integer id);

    String findName(Integer id);
}
