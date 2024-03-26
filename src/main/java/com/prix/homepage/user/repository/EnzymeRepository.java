package com.prix.homepage.user.repository;

import java.util.List;

import com.prix.homepage.user.domain.Enzyme;


public interface EnzymeRepository {
    List<Enzyme> getAllEnzymeByUserId(Integer userId);
}
