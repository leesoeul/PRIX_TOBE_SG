package com.prix.homepage.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.pojo.Account;

import java.util.Optional;

@Mapper
@Repository
public interface AccountMapper {

  void insert(Account account);

  Optional<Account> selectById(int id);

  void deleteById(int id) throws Exception;

  boolean isEmailExists(String email);

  // Account findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

  Optional<Account> findByEmail(String email);
}
