package com.prix.homepage.user.repository;


import java.util.Optional;

import com.prix.homepage.user.domain.Account;

public interface AccountRepository {
  
  void insertAccount(Account account);

  Optional<Account> selectAccount(int id);

  void deleteAccount(int id) throws Exception;

  boolean isEmailExists(String email);

  Optional<Account> findByEmail(String email);
}
