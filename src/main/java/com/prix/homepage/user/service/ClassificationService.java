package com.prix.homepage.user.service;

import com.prix.homepage.user.pojo.Classification;

public interface ClassificationService {
    Integer selectByClass(String nodeName);

    Integer selectMax();

    void insertNew(String nodeName);

}
