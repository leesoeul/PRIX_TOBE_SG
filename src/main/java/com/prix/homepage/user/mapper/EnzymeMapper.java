package com.prix.homepage.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.prix.homepage.user.domain.Enzyme;

@Mapper
public interface EnzymeMapper {
  List<Enzyme> getAllEnzymeByUserId(Integer userId);
}
