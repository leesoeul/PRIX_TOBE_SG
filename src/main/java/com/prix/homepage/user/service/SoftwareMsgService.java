package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.domain.SoftwareMsg;


public interface SoftwareMsgService {
  
  List<SoftwareMsg> getAllSoftwareMsgById(String id);
  
}
