package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.ModificationUserMapper;
import com.prix.homepage.user.pojo.ModificationUser;
import com.prix.homepage.user.service.ModificationUserService;

import lombok.AllArgsConstructor;

/**
 * ModificationLog 객체와 관련된 서비스
 */
@AllArgsConstructor
@Service

public class ModificationUserServiceImpl implements ModificationUserService {
    private final ModificationUserMapper modificationUserMapper;


    @Override
    public void insertModification(String modName, String fullName, Integer classi, String md, String amd, String residue, String position){
        modificationUserMapper.insertModification(modName, fullName, classi, md, amd, residue, position);
    }

    @Override
    public Integer selectMin(){
        return modificationUserMapper.selectMin();
    }

    @Override
    public void updateMod(Integer minUserId){
        modificationUserMapper.updateMod(minUserId);
    }
}
