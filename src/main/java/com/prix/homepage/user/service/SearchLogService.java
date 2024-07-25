package com.prix.homepage.user.service;

import java.util.List;
import com.prix.homepage.user.pojo.SearchLog;

public interface SearchLogService {

    List<SearchLog> getAllSearchLog();
    
    String findFile(Integer id);

    String findName(Integer id);
}
