package com.prix.homepage.livesearch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.dao.PatternMatchMapper;
import com.prix.homepage.livesearch.pojo.PatternMatch;
import com.prix.homepage.livesearch.service.PatternMatchService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PatternMatchServiceImpl implements PatternMatchService {

    private final PatternMatchMapper patternMatchMapper;

    @Override
    public String getUpdateDay(String dbname) {

        String latestUpdate = "";
        List<PatternMatch> patternMatch = null;

        if (dbname == "genbank") {
            patternMatch = patternMatchMapper.findUpdateDayGenbank();

        } else if (dbname == "swiss_prot") {
            patternMatch = patternMatchMapper.findUpdateDaySwissProt();

        }

        if (!patternMatch.isEmpty()) {
            latestUpdate = patternMatch.get(patternMatch.size() - 1).getDate();
        }

        return latestUpdate;
    }
}
