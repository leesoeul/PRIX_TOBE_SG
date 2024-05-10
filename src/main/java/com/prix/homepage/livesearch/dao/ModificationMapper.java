package com.prix.homepage.livesearch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.pojo.Modification;

@Mapper
@Repository
public interface ModificationMapper {
  
  //var_ptms_list에서 사용
  //"select id, name, mass_diff, residue, position from px_modification 
  //where id in 
  //(select mod_id from px_user_modification 
  // where user_id=" + id + " and variable=" + var + " and engine=" + engine + ")" + ((sortBy == null || sortBy.length() == 0) ? ";" : (" order by " + sortBy + ";"));
  List<Modification> findModListByUserMod(Integer userId, Integer variable, Integer engine, String sortBy);

}
