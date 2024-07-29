package com.prix.homepage.livesearch.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.pojo.JobQueue;
import com.prix.homepage.livesearch.service.JobQueueService;
import com.prix.homepage.livesearch.dao.JobQueueMapper;


import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JobQueueServiceImpl implements JobQueueService{
    private final JobQueueMapper jobQueueMapper;

    @Override
    @Transactional
    public List<JobQueue> selectJCandTitle(Integer userId){
        return jobQueueMapper.selectJCandTitle(userId);
    }
}
