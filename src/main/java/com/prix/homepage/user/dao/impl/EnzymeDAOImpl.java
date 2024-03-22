package com.prix.homepage.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prix.homepage.livesearch.entity.Enzyme;
import com.prix.homepage.livesearch.repository.EnzymeRepository;
import com.prix.homepage.user.dao.EnzymeDAO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EnzymeDAOImpl implements EnzymeDAO{

  EnzymeRepository enzymeRepository;

  @Override
  public List<Enzyme> getAllEnzymeByUserId(Integer userId) {
    return enzymeRepository.findAllByUserId(userId);
  }
  
}
