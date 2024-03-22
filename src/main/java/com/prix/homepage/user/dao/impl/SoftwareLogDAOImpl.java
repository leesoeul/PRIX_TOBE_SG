package com.prix.homepage.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prix.homepage.user.dao.SoftwareLogDAO;
import com.prix.homepage.user.entity.SoftwareLog;
import com.prix.homepage.user.repository.SoftwareLogRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Component
public class SoftwareLogDAOImpl implements SoftwareLogDAO{

  SoftwareLogRepository softwareLogRepository;

  @Override
  public List<SoftwareLog> getAllSoftwareLog() {
    return softwareLogRepository.findAllByOrderByDateAscIdAsc();
  }
  
}
