package com.prix.homepage.user.service;

import java.util.List;
import com.prix.homepage.user.pojo.SearchLog;

public interface SearchLogService {

    List<SearchLog> getAllSearchLog(Integer userId);
    
    List<SearchLog> getSearchLogs(int userId, int offset, int pageSize);
    
    int getTotalLogs(int userId);
}
