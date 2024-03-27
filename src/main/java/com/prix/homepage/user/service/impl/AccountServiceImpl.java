package com.prix.homepage.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prix.homepage.user.dao.AccountMapper;
import com.prix.homepage.user.pojo.Account;
import com.prix.homepage.user.service.AccountService;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountMapper accountMapper;
  private final PasswordEncoder passwordEncoder;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void saveAccount(Account accountDto) {
    String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
    logger.info("encodedPW: {}", encodedPassword);

    accountMapper.insert(
        Account.builder()
            .id(accountDto.getId())
            .userid(accountDto.getUserid())
            .password(encodedPassword)
            .name(accountDto.getName())
            .email(accountDto.getEmail())
            .affiliation(accountDto.getAffiliation())
            .level(accountDto.getLevel())
            .build());
  }

  @Override
  public Account getAccount(int id) {
    Optional<Account> optionalAccount = accountMapper.selectById(id);
    Account account = optionalAccount.orElse(null);

    return account;
  }

  @Override
  public void deleteAccount(int id) throws Exception {
    accountMapper.deleteById(id);
  }

  @Override
  public boolean isEmailExists(String email) {
    logger.info("email check");
    return accountMapper.isEmailExists(email);
  }

  @Override
  public Account findByEmailAndPassword(String email, String password) {
    Optional<Account> optionalAccount = accountMapper.findByEmail(email);
    Account account = optionalAccount.orElse(null);
    if (account != null && passwordEncoder.matches(password, account.getPassword())) {
      return account;
    }
    return null;
  }
}
