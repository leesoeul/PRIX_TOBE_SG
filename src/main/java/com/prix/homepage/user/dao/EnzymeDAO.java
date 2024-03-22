package com.prix.homepage.user.dao;
import java.util.List;

import com.prix.homepage.livesearch.entity.Enzyme;

public interface EnzymeDAO {
  
  List<Enzyme> getAllEnzymeByUserId(Integer userId);
}
