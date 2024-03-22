package com.prix.homepage.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prix.homepage.user.dao.SoftwareMsgDAO;
import com.prix.homepage.user.entity.SoftwareMsg;
import com.prix.homepage.user.repository.SoftwareMsgRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SoftwareMsgDAOImpl implements SoftwareMsgDAO{

  SoftwareMsgRepository softwareMsgRepository;

  @Override
  public List<SoftwareMsg> getAllSoftwareMsgById(String id) {
    return softwareMsgRepository.findAllById(id);
  }
  
  
}
