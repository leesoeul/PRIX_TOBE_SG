package com.prix.homepage.user.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prix.homepage.user.domain.SoftwareLog;
import com.prix.homepage.user.mapper.SoftwareLogMapper;
import com.prix.homepage.user.repository.SoftwareLogRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SoftwareLogRepositoryImpl implements SoftwareLogRepository{

  private final SoftwareLogMapper softwareLogMapper;

  @Override
  public List<SoftwareLog> findAll() {
    return softwareLogMapper.findAll();
  }
}
