package com.prix.homepage.user.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.Modification;

@Mapper
@Repository
public interface ModificationMapper {
    void insertModification(String modName, String fullName, Integer classi, String md, String amd, String residue, String position);

    Integer selectMin();

    void updateMod(Integer minUserId);

}
