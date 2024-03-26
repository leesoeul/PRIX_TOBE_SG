package com.prix.homepage.user.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.domain.SoftwareMsg;
import com.prix.homepage.user.repository.SoftwareMsgRepository;
import com.prix.homepage.user.service.SoftwareMsgService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SoftwareMsgServiceImpl implements SoftwareMsgService{

  private final SoftwareMsgRepository softwareMsgRepository;

  @Override
  public List<SoftwareMsg> getAllSoftwareMsgById(String id) {
    List<SoftwareMsg> listsSoftwareMsgs = softwareMsgRepository.getAllSoftwareMsgById(id);
    
    List<SoftwareMsg> listSoftwareMsg = new ArrayList<>();
    for(SoftwareMsg softwareMsg : listsSoftwareMsgs){
      listSoftwareMsg.add(
        SoftwareMsg.builder()
            .id(softwareMsg.getId())
            .message(softwareMsg.getMessage())
            .build()
      );
    }

    return listSoftwareMsg;
  }

  
}
