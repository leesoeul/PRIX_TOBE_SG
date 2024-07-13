package com.prix.homepage.user.dao;

import com.prix.homepage.user.pojo.SearchLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchLogMapper {
    List<SearchLog> findAll(Integer userId);

    List<SearchLog> findLogs(@Param("userId") int userId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int countLogs(@Param("userId") int userId);
}
