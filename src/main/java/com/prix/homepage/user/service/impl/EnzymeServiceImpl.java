package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prix.homepage.user.dao.EnzymeMapper;
import com.prix.homepage.user.pojo.Enzyme;
import com.prix.homepage.user.service.EnzymeService;

import lombok.AllArgsConstructor;

/**
 * Enzyme 객체와 관련된 서비스
 */
@AllArgsConstructor
@Service
public class EnzymeServiceImpl implements EnzymeService{
  
  private final EnzymeMapper enzymeMapper;

  /**
   * db에서 userId가 일치하는 px_enzyme 테이블 상의 데이터를 모두 가져옴
   */
  @Override
  public List<Enzyme> getAllEnzymeByUserId(Integer userId) {
    List<Enzyme> listEnzymes =  enzymeMapper.findByUserId(userId);

    List<Enzyme> listEnzyme = new ArrayList<>();

    for(Enzyme enzyme : listEnzymes){
      listEnzyme.add(
        Enzyme.builder()
            .id(enzyme.getId())
            .name(enzyme.getName())
            .userId(enzyme.getUserId())
            .ctCleave(enzyme.getCtCleave())
            .ntCleave(enzyme.getNtCleave())
            .build()
      );
    }

    return listEnzyme;
  }

  @Override
  public Integer selectIdByUserIdAndName(Integer userId, String name) {
    return enzymeMapper.selectIdByUserIdAndName(userId, name);
  }

  @Override
  public void insertEnzyme(Integer userId, String name, String ntCleave, String ctCleave) throws Exception
  {
    enzymeMapper.insertEnzyme(userId, name, ntCleave, ctCleave);
  }

  @Override
  public void deleteEnzyme(Integer id, Integer userId) throws Exception {
    enzymeMapper.deleteEnzyme(id, userId);
  }

  @Override
  @Transactional
  public void updateEnzyme(Integer id, Integer userId, String name, String ntCleave, String ctCleave) throws Exception {
    Enzyme enzyme = Enzyme.builder()
        .id(id)
        .userId(userId)
        .name(name)
        .ntCleave(ntCleave)
        .ctCleave(ctCleave)
        .build();
    enzymeMapper.updateEnzyme(enzyme);
  }
  @Override
  @Transactional
  public Enzyme selectById(Integer id) {
    return enzymeMapper.selectById(id);
  }

}
