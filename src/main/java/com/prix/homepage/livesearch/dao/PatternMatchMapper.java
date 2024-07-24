package com.prix.homepage.livesearch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.pojo.PatternMatch;

@Mapper
@Repository
public interface PatternMatchMapper {

    @Select("SELECT * FROM pm_update_table WHERE dbname = 'swiss_prot'")
    public List<PatternMatch> findUpdateDayGenbank();

    @Select("SELECT * FROM pm_update_table WHERE dbname = 'genbank'")
    public List<PatternMatch> findUpdateDaySwissProt();
}
