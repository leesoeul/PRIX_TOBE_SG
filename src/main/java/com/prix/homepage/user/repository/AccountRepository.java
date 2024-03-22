package com.prix.homepage.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prix.homepage.user.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
  
  //회원가입시 아이디 중복 체크
  boolean existsByEmail(String email);

  // //로그인
  // Account findByEmailAndPassword(String email, String password);

  //로그인
  Account findByEmail(String email);

}
