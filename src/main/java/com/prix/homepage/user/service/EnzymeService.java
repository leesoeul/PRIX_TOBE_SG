package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.dto.enzymeDto.EnzymeResponseDto;

public interface EnzymeService {
  
  List<EnzymeResponseDto> getAllEnzymeByUserId(Integer userId);
  
}
