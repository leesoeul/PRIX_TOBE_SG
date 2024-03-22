package com.prix.homepage.user.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.SoftwareLogDAO;
import com.prix.homepage.user.dto.softwareLogDto.SoftwareLogResponseDto;
import com.prix.homepage.user.entity.SoftwareLog;
import com.prix.homepage.user.service.SoftwareLogService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SoftwareLogServiceImpl implements SoftwareLogService{

  private final SoftwareLogDAO softwareLogDAO;

  @Override
  public List<SoftwareLogResponseDto> getAllSoftwareLog() {
    List<SoftwareLog> listSoftwareLogs = softwareLogDAO.getAllSoftwareLog();

    List<SoftwareLogResponseDto> listSoftwareLogResponseDto = new ArrayList<>();
    for(SoftwareLog softwareLog : listSoftwareLogs){
      listSoftwareLogResponseDto.add(
        SoftwareLogResponseDto.builder()
            .id(softwareLog.getId())
            .name(softwareLog.getName())
            .date(softwareLog.getDate())
            .version(softwareLog.getVersion())
            .file(softwareLog.getFile())
            .build()
      );
    }


    return listSoftwareLogResponseDto;
  }

}
