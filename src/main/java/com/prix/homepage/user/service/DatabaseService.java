package com.prix.homepage.user.service;

import java.util.List;

import com.prix.homepage.user.dto.databaseDto.DatabaseResponseDto;

public interface DatabaseService {

  List<DatabaseResponseDto>  getAllDatabase();
  
}
