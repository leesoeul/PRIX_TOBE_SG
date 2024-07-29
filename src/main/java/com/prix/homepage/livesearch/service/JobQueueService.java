package com.prix.homepage.livesearch.service;

import java.util.List;

import com.prix.homepage.livesearch.pojo.JobQueue;

public interface JobQueueService {
    
    List<JobQueue> selectJCandTitle(Integer userId);

}
