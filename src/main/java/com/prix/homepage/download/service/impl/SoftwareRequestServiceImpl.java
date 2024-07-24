package com.prix.homepage.download.service.impl;

import com.prix.homepage.download.dao.SoftwareRequestMapper;
import com.prix.homepage.download.pojo.SoftwareRequest;
import com.prix.homepage.download.service.SoftwareRequestService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class SoftwareRequestServiceImpl implements SoftwareRequestService {

  private final SoftwareRequestMapper softwareRequestMapper;

  @Override
  public void insert(String name, String affiliation, String title, String email, String instrument,
      String software) {

    softwareRequestMapper.saveSoftwareRequest(SoftwareRequest.builder()
        .date(LocalDateTime.now())
        .name(name)
        .affiliation(affiliation)
        .title(title)
        .email(email)
        .instrument(instrument)
        .software(software)
        .state(0)
        .build());
  }

}
