package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.prix.homepage.user.pojo.SoftwareMsg;

@Mapper
public interface SoftwareMsgMapper {
    List<SoftwareMsg> findById(String id);

    String findByName(String id);

    void updateSoftwareMsg(@Param("id") String id, @Param("message") String message);
}
