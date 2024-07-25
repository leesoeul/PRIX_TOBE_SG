package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;
//import org.eclipse.tags.shaded.org.apache.regexp.RE;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prix.homepage.user.dao.RequestLogMapper;
import com.prix.homepage.user.pojo.RequestLog;
import com.prix.homepage.user.service.RequestLogService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RequestLogServiceImpl implements RequestLogService{
    private final RequestLogMapper requestLogMapper;

    @Override
    public List<RequestLog> getAllRequestLog() {
        List<RequestLog> listRequestLogs = requestLogMapper.findAll();
        List<RequestLog> listRequestLog = new ArrayList<>();
        for(RequestLog RequestLog : listRequestLogs) {
            listRequestLog.add(
                RequestLog.builder()
                    .id(RequestLog.getId())
                    .date(RequestLog.getDate())
                    .name(RequestLog.getName())
                    .affiliation(RequestLog.getAffiliation())
                    .title(RequestLog.getTitle())
                    .email(RequestLog.getEmail())
                    .instrument(RequestLog.getInstrument())
                    .software(RequestLog.getSoftware())
                    .version(RequestLog.getVersion())
                    .state(RequestLog.getState())
                    .build()
            );
        }
        return listRequestLog;
    }

    @Override
    @Transactional
    public void deleteRequest(Integer id) {
        requestLogMapper.deleteRequest(id);
    }

    @Override
    @Transactional
    public void updateState(Integer id, Integer state) {
        requestLogMapper.updateState(id, state);
    }
}
