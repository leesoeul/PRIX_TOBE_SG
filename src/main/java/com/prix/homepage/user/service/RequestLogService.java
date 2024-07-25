package com.prix.homepage.user.service;

import java.util.List;
import com.prix.homepage.user.pojo.RequestLog;

public interface RequestLogService {

    List<RequestLog> getAllRequestLog();

    void deleteRequest(Integer id);

    void updateState(Integer id, Integer state);
    
}
