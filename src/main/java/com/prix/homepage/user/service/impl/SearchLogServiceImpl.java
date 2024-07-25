package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prix.homepage.user.dao.SearchLogMapper;
import com.prix.homepage.user.pojo.SearchLog;
import com.prix.homepage.user.service.SearchLogService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SearchLogServiceImpl implements SearchLogService {
    private final SearchLogMapper searchLogMapper;

    @Override
    public List<SearchLog> getAllSearchLog() {
        List<SearchLog> listSearchLogs = searchLogMapper.findAll();
        List<SearchLog> listSearchLog = new ArrayList<>();
        for(SearchLog searchLog : listSearchLogs) {
            listSearchLog.add(
                SearchLog.builder()
                    .id(searchLog.getId())
                    .user_id(searchLog.getUser_id())
                    .title(searchLog.getTitle())
                    .date(searchLog.getDate())
                    .msfile(searchLog.getMsfile())
                    .db(searchLog.getDb())
                    .result(searchLog.getResult())
                    .engine(searchLog.getEngine())
                    .build()
            );
        }
        return listSearchLog;
    }

    @Override
    @Transactional
    public String findFile(Integer id) {
        return searchLogMapper.findFile(id);
    }

    @Override
    @Transactional
    public String findName(Integer id) {
        return searchLogMapper.findName(id);
    }
}
