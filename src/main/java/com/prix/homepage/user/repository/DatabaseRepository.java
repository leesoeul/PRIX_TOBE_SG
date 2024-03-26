package com.prix.homepage.user.repository;

import java.util.List;

import com.prix.homepage.user.domain.Database;


public interface DatabaseRepository {
  List<Database> getAllDatabase();
}