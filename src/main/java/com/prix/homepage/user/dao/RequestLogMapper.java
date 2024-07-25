package com.prix.homepage.user.dao;

import com.prix.homepage.user.pojo.RequestLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RequestLogMapper {

    List<RequestLog> findAll();

    void deleteRequest(Integer id);

    void updateState(Integer id, Integer state);
    
}
