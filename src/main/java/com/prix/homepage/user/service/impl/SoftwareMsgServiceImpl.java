package com.prix.homepage.user.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.SoftwareMsgDAO;
import com.prix.homepage.user.dto.softwareMsgDto.SoftwareMsgResponseDto;
import com.prix.homepage.user.entity.SoftwareMsg;
import com.prix.homepage.user.service.SoftwareMsgService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SoftwareMsgServiceImpl implements SoftwareMsgService{

  private final SoftwareMsgDAO softwareMsgDAO;

  @Override
  public List<SoftwareMsgResponseDto> getAllSoftwareMsgById(String id) {
    List<SoftwareMsg> listsSoftwareMsgs = softwareMsgDAO.getAllSoftwareMsgById(id);
    
    List<SoftwareMsgResponseDto> listSoftwareMsgResponseDto = new ArrayList<>();
    for(SoftwareMsg softwareMsg : listsSoftwareMsgs){
      listSoftwareMsgResponseDto.add(
        SoftwareMsgResponseDto.builder()
            .id(softwareMsg.getId())
            .message(softwareMsg.getMessage())
            .build()
      );
    }

    return listSoftwareMsgResponseDto;
  }

  
}
