package com.prix.homepage.user.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.prix.homepage.user.dao.AccountDAO;
import com.prix.homepage.user.entity.Account;
import com.prix.homepage.user.repository.AccountRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AccountDAOImpl implements AccountDAO{

  private final AccountRepository accountRepository;

  @Override
  public Account insertAccount(Account account) {
    Account savedAccount = accountRepository.save(account);
    
    return savedAccount;
  }

  @Override
  public Account selecAccount(int number) {
    Optional<Account> optionalAccount = accountRepository.findById(number);
    Account selectedAccount;
    if(optionalAccount.isPresent()){
      selectedAccount = optionalAccount.get();
    }else{
      selectedAccount = null;
      // throw new Exception();
    }
    return selectedAccount;
  }

  @Override
  public void deleteAccount(int number) throws Exception {
    Optional<Account> optionalAccount = accountRepository.findById(number);
    Account selectedAccount;
    if(optionalAccount.isPresent()){
      selectedAccount = optionalAccount.get();
    }else{
      selectedAccount = null;
      throw new Exception();
    }
    accountRepository.delete(selectedAccount);
  }

  @Override
  public boolean isEmailExists(String email) {
      return accountRepository.existsByEmail(email);
  }

  // @Override
  // public Account findByEmailAndPassword(String email, String password) {
  //   return accountRepository.findByEmailAndPassword(email, password);
  // }

  @Override
  public Account findByEmail(String email) {
    return accountRepository.findByEmail(email);
  }
  
  
  
}
