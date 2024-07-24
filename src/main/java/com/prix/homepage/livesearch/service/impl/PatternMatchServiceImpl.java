package com.prix.homepage.livesearch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.dao.PatternMatchMapper;
import com.prix.homepage.livesearch.pojo.PatternMatch;
import com.prix.homepage.livesearch.service.PatternMatchService;

import lombok.AllArgsConstructor;

/**
 * Pattern match 객체와 관련된 서비스
 */
@AllArgsConstructor
@Service
public class PatternMatchServiceImpl implements PatternMatchService {

    private final PatternMatchMapper patternMatchMapper;

    /**
     * update 날짜를 db에서 가져옴
     */
    @Override
    public String getUpdateDay(String dbname) {

        String latestUpdate = "";
        List<PatternMatch> patternMatch = null;

        if (dbname == "genbank") {
            patternMatch = patternMatchMapper.findUpdateDayGenbank();

        } else if (dbname == "swiss_prot") {
            patternMatch = patternMatchMapper.findUpdateDaySwissProt();

        }

        /**
         * 받아온 date 중 가장 최신 날짜를 저장하여 반환
         */
        if (!patternMatch.isEmpty()) {
            latestUpdate = patternMatch.get(patternMatch.size() - 1).getDate();
        }

        return latestUpdate;
    }
}
