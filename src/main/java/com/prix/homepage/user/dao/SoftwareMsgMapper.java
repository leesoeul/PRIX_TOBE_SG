package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.prix.homepage.user.pojo.SoftwareMsg;

@Mapper
public interface SoftwareMsgMapper {
    List<SoftwareMsg> findById(String id);

    void updateSoftwareMsg(String id, String message);
}
