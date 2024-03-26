package com.prix.homepage.user.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.domain.SoftwareLog;
import com.prix.homepage.user.repository.SoftwareLogRepository;
import com.prix.homepage.user.service.SoftwareLogService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SoftwareLogServiceImpl implements SoftwareLogService{

  private final SoftwareLogRepository softwareLogRepository;

  @Override
  public List<SoftwareLog> getAllSoftwareLog() {
    List<SoftwareLog> listSoftwareLogs = softwareLogRepository.findAll();

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
