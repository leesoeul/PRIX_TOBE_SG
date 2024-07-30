package com.prix.homepage.user.service;

import java.sql.Date;
import java.util.List;

import com.prix.homepage.user.pojo.ModificationLog;


public interface ModificationLogService {
  
  List<ModificationLog> getAllModificationLog();

  void insertModLog(Date modDate, String modVersion, String modFile);

}
