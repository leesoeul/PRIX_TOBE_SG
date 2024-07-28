package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.SoftwareLog;

@Mapper
@Repository
public interface SoftwareLogMapper {
  List<SoftwareLog> findAll();

  void insertSoftLog(String sftwName, String sftwDate, String sftwVersion, String filePath);
}
