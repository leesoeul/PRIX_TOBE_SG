package com.prix.homepage.user.dao;

import com.prix.homepage.user.pojo.SearchLogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchLogUserMapper {
    List<SearchLogUser> findAll();

    String findFile(Integer id);

    String findName(Integer id);

    List<SearchLogUser> findByUserId(@Param("userId") Integer userId);
}
