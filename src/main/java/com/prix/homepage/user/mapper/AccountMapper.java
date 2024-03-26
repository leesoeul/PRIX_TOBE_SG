package com.prix.homepage.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.prix.homepage.user.domain.Account;

import java.util.Optional;

@Mapper
public interface AccountMapper {

  void insertAccount(Account account);

  Optional<Account> selectAccount(int id);

  void deleteAccount(int id) throws Exception;

  boolean isEmailExists(String email);

  // Account findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

  Optional<Account> findByEmail(String email);
}
