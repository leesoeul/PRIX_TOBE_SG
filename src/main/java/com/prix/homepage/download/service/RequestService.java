package com.prix.homepage.download.service;

import com.prix.homepage.download.pojo.SoftwareRequest;
import com.prix.homepage.download.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RequestService {
    
    @Autowired
    private RequestRepository requestRepository;

    public void saveRequest(String name, String affiliation, String title, String email, String instrument, String software) {
        SoftwareRequest request = new SoftwareRequest();
        request.setDate(new Timestamp(System.currentTimeMillis()));
        request.setName(name);
        request.setAffiliation(affiliation);
        request.setTitle(title);
        request.setEmail(email);
        request.setInstrument(instrument);
        request.setSoftware(software);
        request.setState(0);
        
        request.setSenttime(null);
        requestRepository.save(request);
    }

}
