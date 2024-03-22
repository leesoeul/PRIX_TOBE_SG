package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.dto.softwareLogDto.SoftwareLogResponseDto;

public interface SoftwareLogService {
  
  List<SoftwareLogResponseDto> getAllSoftwareLog();
}
