package com.prix.homepage.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.prix.homepage.user.pojo.Account;
import com.prix.homepage.user.pojo.NewAccount;
import com.prix.homepage.user.service.AccountService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class RegisterController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final AccountService accountService;

  @GetMapping("/confirm_personalInfo")
  public String gotoConfirmPersonalInfoPage() {
    return "confirm_personalInfo";
  }

  @GetMapping("/registration")
  public String gotoRegistrationPage(Model model) {

    NewAccount newAccountDto = NewAccount.builder()
        .userid(null)
        .password("")
        .name(null)
        .email(null)
        .affiliation(null)
        .level(1)
        .confirmedPassword("")
        .build();

    model.addAttribute("newAccountDto", newAccountDto);

    return "registration";
  }

  @PostMapping("/registration")
  public String registerNewUser(Model model, @Valid NewAccount newAccountDto, BindingResult result) {
    if (result.hasErrors()) {
      logger.warn("유효성 검사에서 탈락");
      // 유효성 검사 실패 이유 확인
      for (ObjectError error : result.getAllErrors()) {
        logger.warn("유효성 실패 원인: " + error.getDefaultMessage());
      }
      model.addAttribute("newAccountDto", newAccountDto);
      return "registration";
    }

    // 비밀번호 확인
    if (!newAccountDto.getPassword().equals(newAccountDto.getConfirmedPassword())) {
      result.rejectValue("confirmedPassword", "password.mismatch", "Passwords do not match");
      logger.warn("비번 검사에서 탈락");
      model.addAttribute("newAccountDto", newAccountDto);
      return "registration";
    }
    // 이메일 중복 확인
    if (accountService.isEmailExists(newAccountDto.getEmail())) {
      result.rejectValue("email", "email.exists", "Email already exists");
      logger.warn("이메일 검사에서 탈락");
      model.addAttribute("newAccountDto", newAccountDto);
      return "registration";
    }

    String name = newAccountDto.getEmail();
    String job = "";

    // db 저장용으로 변환
    Account accountDto = Account.builder()
        .userid(newAccountDto.getUserid())
        .password(newAccountDto.getPassword())
        .name(name)
        .email(newAccountDto.getEmail())
        .affiliation(job)
        .level(newAccountDto.getLevel())
        .build();

    accountService.saveAccount(accountDto);

    return "redirect:login";
  }

}
