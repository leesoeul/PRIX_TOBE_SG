package com.prix.homepage.user.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prix.homepage.user.domain.Enzyme;
import com.prix.homepage.user.mapper.EnzymeMapper;
import com.prix.homepage.user.repository.EnzymeRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EnzymeRepositoryImpl implements EnzymeRepository {

  private final EnzymeMapper enzymeMapper;

  @Override
  public List<Enzyme> getAllEnzymeByUserId(Integer userId) {
    return enzymeMapper.getAllEnzymeByUserId(userId);
  }
  
}
