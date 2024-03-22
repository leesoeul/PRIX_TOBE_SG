package com.prix.homepage.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.AccountDAO;
import com.prix.homepage.user.dto.accountDto.AccountDto;
import com.prix.homepage.user.dto.accountDto.AccountResponseDto;
import com.prix.homepage.user.entity.Account;
import com.prix.homepage.user.service.AccountService;



import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountDAO accountDAO;
  private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


  @Override
  public AccountResponseDto getAccount(int number) {
    Account account = accountDAO.selecAccount(number);

    return createAccountResponseDto(account);
  }

  @Override
  public AccountResponseDto saveAccount(AccountDto accountDto) {
    String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
    logger.info("encodedPW: {}", encodedPassword);

    Account savedAccount = accountDAO.insertAccount(
            Account.builder()
                    .userid(accountDto.getUserid())
                    .password(encodedPassword)
                    .name(accountDto.getName())
                    .email(accountDto.getEmail())
                    .affiliation(accountDto.getAffiliation())
                    .level(accountDto.getLevel())
                    .build()
    );

    return createAccountResponseDto(savedAccount);
  }

  @Override
  public void deleteAccount(int number) throws Exception {
    accountDAO.deleteAccount(number);
  }
   
  @Override
  public boolean isEmailExists(String email){
    return accountDAO.isEmailExists(email);
  }

  @Override
  public AccountResponseDto findByEmailAndPassword(String email, String password) {
    Account account = accountDAO.findByEmail(email);
    if (account != null && passwordEncoder.matches(password, account.getPassword())) {
      return createAccountResponseDto(account);
    }
    return null;
  }


  private AccountResponseDto createAccountResponseDto(Account account) {
    if (account == null) {
        return null;
    }
    return AccountResponseDto.builder()
            .id(account.getId())
            .userid(account.getUserid())
            .password(account.getPassword())
            .name(account.getName())
            .email(account.getEmail())
            .affiliation(account.getAffiliation())
            .level(account.getLevel())
            .build();
  }
  
}
