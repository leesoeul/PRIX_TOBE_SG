package com.prix.homepage.livesearch.service;

import com.prix.homepage.livesearch.pojo.UserSettingDto;

public interface UserSettingService {
  UserSettingDto getUsersetting(Integer id, String engine);
}
