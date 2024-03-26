package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.user.domain.Enzyme;
import com.prix.homepage.user.repository.EnzymeRepository;
import com.prix.homepage.user.service.EnzymeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EnzymeServiceImpl implements EnzymeService{
  
  private final EnzymeRepository enzymeRepository;

  @Override
  public List<Enzyme> getAllEnzymeByUserId(Integer userId) {
    List<Enzyme> listEnzymes =  enzymeRepository.getAllEnzymeByUserId(userId);

    List<Enzyme> listEnzyme = new ArrayList<>();

    for(Enzyme enzyme : listEnzymes){
      listEnzyme.add(
        Enzyme.builder()
            .id(enzyme.getId())
            .name(enzyme.getName())
            .user_id(enzyme.getUser_id())
            .ct_cleave(enzyme.getCt_cleave())
            .nt_cleave(enzyme.getNt_cleave())
            .build()
      );
    }

    return listEnzyme;
  }

}
