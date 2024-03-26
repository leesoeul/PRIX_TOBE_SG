package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.domain.ModificationLog;
import com.prix.homepage.user.repository.ModificationLogRepository;
import com.prix.homepage.user.service.ModificationLogService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ModificationLogServiceImpl implements ModificationLogService{

  private final ModificationLogRepository modificationLogRepository;

  @Override
  public List<ModificationLog> getAllModificationLog() {
    List<ModificationLog> listModificationLogs = modificationLogRepository.getAllModificationLog();

    List<ModificationLog> listModificationLog = new ArrayList<>();
    for(ModificationLog modificationLog : listModificationLogs){
      listModificationLog.add(
        ModificationLog.builder()
            .id(modificationLog.getId())
            .date(modificationLog.getDate())
            .version(modificationLog.getVersion())
            .file(modificationLog.getFile())  
            .build()
      );
    }

    return listModificationLog;
  }
  
}
