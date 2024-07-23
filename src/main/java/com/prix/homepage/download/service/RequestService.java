package com.prix.homepage.download.service;

import com.prix.homepage.download.pojo.SoftwareRequest;

public interface RequestService {

    void insert(String name, String affiliation, String title, String email,
            String instrument,
            String software) throws Exception;

    void insertRequest(SoftwareRequest request) throws Exception;
}
