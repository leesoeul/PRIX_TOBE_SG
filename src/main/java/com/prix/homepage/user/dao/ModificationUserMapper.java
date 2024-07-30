package com.prix.homepage.user.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.ModificationUser;

@Mapper
@Repository
public interface ModificationUserMapper {
    void insertModification(@Param("modName") String modName, @Param("fullName") String fullName, @Param("classMap") Integer classi, @Param("md") String md, @Param("amd") String amd, @Param("residue") String residue, @Param("position") String position);

    Integer selectMin();

    void updateMod(Integer minUserId);

}
