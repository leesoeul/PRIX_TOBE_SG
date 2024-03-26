package com.prix.homepage.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.prix.homepage.user.domain.SoftwareLog;

@Mapper
public interface SoftwareLogMapper {
  List<SoftwareLog> findAll();
}
