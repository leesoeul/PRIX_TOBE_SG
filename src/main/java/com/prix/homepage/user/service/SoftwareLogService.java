package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.pojo.SoftwareLog;


public interface SoftwareLogService {
  
  List<SoftwareLog> getAllSoftwareLog();

  void insertSoftLog(String sftwName, String sftwDate, String sftwVersion, String filePath);
}
