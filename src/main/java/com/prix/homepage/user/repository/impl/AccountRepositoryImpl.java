package com.prix.homepage.user.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.prix.homepage.user.domain.Account;
import com.prix.homepage.user.mapper.AccountMapper;
import com.prix.homepage.user.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

  private final AccountMapper accountMapper;

  @Override
  public void insertAccount(Account account) {
    accountMapper.insertAccount(account);
  }

  @Override
  public Optional<Account> selectAccount(int id) {
    return accountMapper.selectAccount(id);

  }

  @Override
  public void deleteAccount(int id) throws Exception {
    accountMapper.deleteAccount(id);
  }

  @Override
  public boolean isEmailExists(String email) {
    return accountMapper.isEmailExists(email);
  }

  @Override
  public Optional<Account> findByEmail(String email) {
    return accountMapper.findByEmail(email);
  }
  
}
