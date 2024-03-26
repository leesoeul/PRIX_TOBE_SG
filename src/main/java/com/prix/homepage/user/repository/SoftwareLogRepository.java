package com.prix.homepage.user.repository;

import java.util.List;

import com.prix.homepage.user.domain.SoftwareLog;

public interface SoftwareLogRepository {
    List<SoftwareLog> findAll();
}
