package com.prix.homepage.user.service;

import com.prix.homepage.user.pojo.Account;

public interface AccountService {

  void saveAccount(Account accountDto);

  Account getAccount(int id);

  void deleteAccount(int id) throws Exception;

  boolean isEmailExists(String email);
  
  Account findByEmailAndPassword(String email, String password);
}