package com.prix.homepage.download.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.prix.homepage.download.pojo.SoftwareRequest;

@Mapper
@Repository
public interface RequestMapper {

    // request에서 사용
    @Insert("INSERT INTO px_software_request (date, name, affiliation, title, email, instrument, software, version, state, senttime) VALUES (#{date}, #{name}, #{affiliation}, #{title}, #{email}, #{instrument}, #{software}, #{version}, #{state}, #{senttime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveRequest(SoftwareRequest request);
    // void saveRequest(Timestamp date, String name, String affiliation,String
    // title, String email, String instrument,String software, String version, int
    // state, Timestamp senttime);

    // void insert(String name, String affiliation, String title, String email,
    // String instrument,
    // String software);

}
