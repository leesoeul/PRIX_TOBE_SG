package com.prix.homepage.user.repository;

import java.util.List;

import com.prix.homepage.user.domain.ModificationLog;

public interface ModificationLogRepository {
    List<ModificationLog> getAllModificationLog();
}
