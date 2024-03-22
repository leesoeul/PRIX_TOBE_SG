package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.dto.softwareMsgDto.SoftwareMsgResponseDto;

public interface SoftwareMsgService {
  
  List<SoftwareMsgResponseDto> getAllSoftwareMsgById(String id);
  
}
