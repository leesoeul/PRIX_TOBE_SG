package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.ModificationMapper;
import com.prix.homepage.user.pojo.Modification;
import com.prix.homepage.user.service.ModificationService;

import lombok.AllArgsConstructor;

/**
 * ModificationLog 객체와 관련된 서비스
 */
@AllArgsConstructor
@Service

public class ModificationServiceImpl implements ModificationService {
    private final ModificationMapper modificationMapper;


    @Override
    public void insertModification(String modName, String fullName, Integer classi, String md, String amd, String residue, String position){
        modificationMapper.insertModification(modName, fullName, classi, md, amd, residue, position);
    }

    @Override
    public Integer selectMin(){
        return modificationMapper.selectMin();
    }

    @Override
    public void updateMod(Integer minUserId){
        modificationMapper.updateMod(minUserId);
    }
}
