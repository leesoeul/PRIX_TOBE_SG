package com.prix.homepage.user.dao;

import com.prix.homepage.user.entity.Account;

public interface AccountDAO {
  Account insertAccount(Account account);

  Account selecAccount(int number);

  void deleteAccount(int number) throws Exception;

  boolean isEmailExists(String email);

  // Account findByEmailAndPassword(String email, String password);

  Account findByEmail(String email);

}
