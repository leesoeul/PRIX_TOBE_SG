package com.prix.homepage.user.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.SoftwareLogMapper;
import com.prix.homepage.user.pojo.SoftwareLog;
import com.prix.homepage.user.service.SoftwareLogService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SoftwareLogServiceImpl implements SoftwareLogService{

  private final SoftwareLogMapper softwareLogMapper;

  @Override
  public List<SoftwareLog> getAllSoftwareLog() {
    List<SoftwareLog> listSoftwareLogs = softwareLogMapper.findAll();

    List<SoftwareLog> listSoftwareLog = new ArrayList<>();
    for(SoftwareLog softwareLog : listSoftwareLogs){
      listSoftwareLog.add(
        SoftwareLog.builder()
            .id(softwareLog.getId())
            .name(softwareLog.getName())
            .date(softwareLog.getDate())
            .version(softwareLog.getVersion())
            .file(softwareLog.getFile())
            .build()
      );
    }


    return listSoftwareLog;
  }

}
