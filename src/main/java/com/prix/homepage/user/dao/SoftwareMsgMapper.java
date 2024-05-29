package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.SoftwareMsg;

@Mapper
@Repository
public interface SoftwareMsgMapper {
    List<SoftwareMsg> findById(String id);

    void update(SoftwareMsg softwareMsg);
}
