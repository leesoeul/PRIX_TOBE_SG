package com.prix.homepage.user.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prix.homepage.user.domain.SoftwareMsg;
import com.prix.homepage.user.mapper.SoftwareMsgMapper;
import com.prix.homepage.user.repository.SoftwareMsgRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SoftwareRepositoryImpl implements SoftwareMsgRepository{

  private final SoftwareMsgMapper softwareMsgMapper;

  @Override
  public List<SoftwareMsg> getAllSoftwareMsgById(String id) {
    return softwareMsgMapper.findAll(id);
  }
  
}
