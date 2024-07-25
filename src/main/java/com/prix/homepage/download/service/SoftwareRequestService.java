package com.prix.homepage.download.service;

public interface SoftwareRequestService {

    void insert(String name, String affiliation, String title, String email,
            String instrument,
            String software) throws Exception;

}
