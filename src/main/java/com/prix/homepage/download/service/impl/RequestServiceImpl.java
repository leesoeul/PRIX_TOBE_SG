package com.prix.homepage.download.service.impl;

import com.prix.homepage.download.dao.RequestMapper;
import com.prix.homepage.download.pojo.SoftwareRequest;
import com.prix.homepage.download.service.RequestService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@AllArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {

  private final RequestMapper requestMapper;

  @Override
  public void insertRequest(SoftwareRequest request) {
    requestMapper.saveRequest(request);
  }

  @Override
  public void insert(String name, String affiliation, String title, String email, String instrument,
      String software) {

    requestMapper.saveRequest(SoftwareRequest.builder()
        .date(new Timestamp(System.currentTimeMillis()))
        .name(name)
        .affiliation(affiliation)
        .title(title)
        .email(email)
        .instrument(instrument)
        .software(software)
        .version("1.0") // Assuming a default version
        .state(0)
        .date(new Timestamp(System.currentTimeMillis()))
        .build());
  }

}
