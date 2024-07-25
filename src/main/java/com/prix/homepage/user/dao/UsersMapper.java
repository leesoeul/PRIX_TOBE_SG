package com.prix.homepage.user.dao;

import com.prix.homepage.user.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsersMapper {
    List<Users> findAll();
    void deleteById(Integer id);
}
