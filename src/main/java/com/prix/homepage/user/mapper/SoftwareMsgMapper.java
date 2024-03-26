package com.prix.homepage.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.prix.homepage.user.domain.SoftwareMsg;

@Mapper
public interface SoftwareMsgMapper {
    List<SoftwareMsg> findAll(String id);
}
