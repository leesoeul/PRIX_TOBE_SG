package com.prix.homepage.user.repository;

import java.util.List;

import com.prix.homepage.user.domain.SoftwareMsg;

public interface SoftwareMsgRepository {
    List<SoftwareMsg> getAllSoftwareMsgById(String id);
}
