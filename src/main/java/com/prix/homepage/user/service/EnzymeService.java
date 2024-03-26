package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.domain.Enzyme;


public interface EnzymeService {
  
  List<Enzyme> getAllEnzymeByUserId(Integer userId);
  
}
