package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.ModificationLogDAO;
import com.prix.homepage.user.dto.modificationLogDto.ModificationLogResponseDto;
import com.prix.homepage.user.entity.ModificationLog;
import com.prix.homepage.user.service.ModificationLogService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ModificationLogServiceImpl implements ModificationLogService{

  private final ModificationLogDAO modificationLogDAO;

  @Override
  public List<ModificationLogResponseDto> getAllModificationLog() {
    List<ModificationLog> listModificationLogs = modificationLogDAO.getAllModificationLog();

    List<ModificationLogResponseDto> listModificationLogResponseDto = new ArrayList<>();
    for(ModificationLog modificationLog : listModificationLogs){
      listModificationLogResponseDto.add(
        ModificationLogResponseDto.builder()
            .id(modificationLog.getId())
            .date(modificationLog.getDate())
            .version(modificationLog.getVersion())
            .file(modificationLog.getFile())  
            .build()
      );
    }

    return listModificationLogResponseDto;
  }
  
}
