package com.prix.homepage.user.service;

import com.prix.homepage.user.dto.accountDto.AccountDto;
import com.prix.homepage.user.dto.accountDto.AccountResponseDto;

public interface AccountService {

  AccountResponseDto getAccount(int number);

  AccountResponseDto saveAccount(AccountDto accountDto);

  void deleteAccount(int number) throws Exception;

  boolean isEmailExists(String email);
  
  AccountResponseDto findByEmailAndPassword(String email, String password);
}