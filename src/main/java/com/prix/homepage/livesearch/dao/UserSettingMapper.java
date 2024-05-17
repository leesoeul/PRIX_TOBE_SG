package com.prix.homepage.livesearch.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.pojo.UserSetting;

@Mapper
@Repository
public interface UserSettingMapper {
    public UserSetting findByUserIdModeye(Integer userId);
}
