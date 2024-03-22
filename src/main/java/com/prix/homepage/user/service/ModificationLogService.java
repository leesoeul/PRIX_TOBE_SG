package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.dto.modificationLogDto.ModificationLogResponseDto;

public interface ModificationLogService {
  
  List<ModificationLogResponseDto> getAllModificationLog();

}
