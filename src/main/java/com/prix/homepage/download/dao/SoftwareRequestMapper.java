package com.prix.homepage.download.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.download.pojo.SoftwareRequest;

@Mapper
@Repository
public interface SoftwareRequestMapper {

    void saveSoftwareRequest(SoftwareRequest request);
}
