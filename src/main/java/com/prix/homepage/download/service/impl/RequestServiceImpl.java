package com.prix.homepage.download.service.impl;

import com.prix.homepage.download.dao.RequestMapper;
import com.prix.homepage.download.pojo.SoftwareRequest;
import com.prix.homepage.download.service.RequestService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@AllArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {

    private final RequestMapper requestMapper;

    @Override
    public void insert(String name, String affiliation, String title, String email, String instrument,
            String software) {

        SoftwareRequest request = SoftwareRequest.builder()
                .date(new Timestamp(System.currentTimeMillis()))
                .name(name)
                .affiliation(affiliation)
                .title(title)
                .email(email)
                .instrument(instrument)
                .software(software)
                .version("1.0") // Assuming a default version
                .state(0)
                .date(new Timestamp(System.currentTimeMillis()))
                .build();

        // SoftwareRequest request = new SoftwareRequest();
        // request.setDate(new Timestamp(System.currentTimeMillis()));
        // request.setName(name);
        // request.setAffiliation(affiliation);
        // request.setTitle(title);
        // request.setEmail(email);
        // request.setInstrument(instrument);
        // request.setSoftware(software);
        // request.setState(

        // request.setSenttime(null);
        // requestRepository.save(request);

        requestMapper.saveRequest(request);
        // requestMapper.saveRequest(new Timestamp(System.currentTimeMillis()), name, affiliation, title, email, instrument,
                // software, "1.0", 0, null);
        // System.out.println("Inserted request ID: " + request.getId());

    }

}
