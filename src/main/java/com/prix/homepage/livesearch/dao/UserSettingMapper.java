package com.prix.homepage.livesearch.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.pojo.UserSetting;

@Mapper
@Repository
public interface UserSettingMapper {
    UserSetting findByUserIdModeye(Integer userId);

    Integer existsByUserId(Integer userId);

    Integer updateByUserIdDbond(Integer userId,
            Integer enzyme,
            Integer missedCleavage,
            Float pTolerance,
            String pUnit,
            Float fTolerance,
            String engine,
            String dataFormat,
            String instrument);

    Integer updateByUserId(Integer userId,
            Integer enzyme,
            Integer missedCleavage,
            Integer minNumEnzTerm,
            Float pTolerance,
            String pUnit,
            Float fTolerance,
            Float minMM,
            Float maxMM,
            String engine,
            String dataFormat,
            String instrument,
            String msResolution,
            String msmsResolution);

    void insertDbond(Integer userId,
            Integer enzyme,
            Integer missedCleavage,
            Float pTolerance,
            String pUnit,
            Float fTolerance,
            String engine,
            String dataFormat,
            String instrument);

    void insert(Integer userId,
            Integer enzyme,
            Integer missedCleavage,
            Integer minNumEnzTerm,
            Float pTolerance,
            String pUnit,
            Float fTolerance,
            Float minMM,
            Float maxMM,
            String engine,
            String dataFormat,
            String instrument,
            String msResolution,
            String msmsResolution);
}
