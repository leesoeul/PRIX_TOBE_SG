package com.prix.homepage.user.service;

import java.util.List;
import com.prix.homepage.user.pojo.SearchLogUser;

public interface SearchLogUserService {

    List<SearchLogUser> getAllSearchLog();
    
    String findFile(Integer id);

    String findName(Integer id);

    List<SearchLogUser> findByUserId(Integer userId);
}