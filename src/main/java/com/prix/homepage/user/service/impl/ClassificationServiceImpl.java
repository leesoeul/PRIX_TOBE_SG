package com.prix.homepage.user.service.impl;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prix.homepage.user.dao.ClassificationMapper;
import com.prix.homepage.user.pojo.Classification;
import com.prix.homepage.user.service.ClassificationService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service

public class ClassificationServiceImpl implements ClassificationService{
    private final ClassificationMapper classificationMapper;

    @Override
    public Integer selectByClass(String nodeName){
        return classificationMapper.selectByClass(nodeName);
    }

    @Override
    public Integer selectMax(){
        return classificationMapper.selectMax();
    }

    @Override
    public void insertNew(String nodeName){
        classificationMapper.insertNew(nodeName);
    }
    
}
