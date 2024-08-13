package com.prix.homepage.livesearch.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.livesearch.pojo.SearchLog;

@Mapper
@Repository
public interface SearchLogMapper {
  void insert(
      Integer userId,
      String title,
      Integer msFile,
      Integer db,
      String result,
      String engine);

  // 이거 db에선 result가 int인데 result.jsp에선 String fileName을
  // 인자로 넣길래 일단 이렇게 함
  Integer findUserId(Integer userId, String result);

  SearchLog getSearchLog(String index);

  String getUserNameById(String userId);

  Integer getMsFileByResult(Integer result);
}
