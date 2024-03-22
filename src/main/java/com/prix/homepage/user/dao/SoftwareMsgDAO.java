package com.prix.homepage.user.dao;

import java.util.List;

import com.prix.homepage.user.entity.SoftwareMsg;

public interface SoftwareMsgDAO {

  List<SoftwareMsg> getAllSoftwareMsgById(String id);

}
