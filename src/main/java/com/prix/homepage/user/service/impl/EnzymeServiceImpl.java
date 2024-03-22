package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.entity.Enzyme;
import com.prix.homepage.user.dao.EnzymeDAO;
import com.prix.homepage.user.dto.enzymeDto.EnzymeResponseDto;
import com.prix.homepage.user.service.EnzymeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EnzymeServiceImpl implements EnzymeService{
  
  private final EnzymeDAO enzymeDAO;

  @Override
  public List<EnzymeResponseDto> getAllEnzymeByUserId(Integer userId) {
    List<Enzyme> listEnzymes =  enzymeDAO.getAllEnzymeByUserId(userId);

    List<EnzymeResponseDto> listEnzymeResponseDto = new ArrayList<>();

    for(Enzyme enzyme : listEnzymes){
      listEnzymeResponseDto.add(
        EnzymeResponseDto.builder()
            .id(enzyme.getId())
            .name(enzyme.getName())
            .user_id(enzyme.getUser_id())
            .ct_cleave(enzyme.getCt_cleave())
            .nt_cleave(enzyme.getNt_cleave())
            .build()
      );
    }

    return listEnzymeResponseDto;
  }

}
