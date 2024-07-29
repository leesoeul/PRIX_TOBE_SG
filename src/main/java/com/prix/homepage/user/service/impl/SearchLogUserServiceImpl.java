package com.prix.homepage.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prix.homepage.user.dao.SearchLogUserMapper;
import com.prix.homepage.user.pojo.SearchLogUser;
import com.prix.homepage.user.service.SearchLogUserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SearchLogUserServiceImpl implements SearchLogUserService {
    private final SearchLogUserMapper searchLogMapper;

    @Override
    public List<SearchLogUser> getAllSearchLog() {
        List<SearchLogUser> listSearchLogs = searchLogMapper.findAll();
        List<SearchLogUser> listSearchLog = new ArrayList<>();
        for(SearchLogUser searchLog : listSearchLogs) {
            listSearchLog.add(
                SearchLogUser.builder()
                    .id(searchLog.getId())
                    .userId(searchLog.getUserId())
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

    @Override
    @Transactional
    public List<SearchLogUser> findByUserId(Integer userId){
        return searchLogMapper.findByUserId(userId);
    }
}
