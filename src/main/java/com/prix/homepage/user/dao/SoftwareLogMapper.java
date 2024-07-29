package com.prix.homepage.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.SoftwareLog;

@Mapper
@Repository
public interface SoftwareLogMapper {
    List<SoftwareLog> findAll();

    void insertSoftLog(@Param("sftwName") String sftwName, 
                       @Param("sftwDate") String sftwDate, 
                       @Param("sftwVersion") String sftwVersion, 
                       @Param("filePath") String filePath);
}
