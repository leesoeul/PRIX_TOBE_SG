package com.prix.homepage.user.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prix.homepage.user.domain.Account;
import com.prix.homepage.user.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AccountService accountService;

    @GetMapping("/login")
    public String gotoLoginPage(Model model, @RequestParam(required = false) Map<String, String> paramsMap,
            HttpServletRequest request) {
        if (paramsMap.containsKey("action") && paramsMap.get("action").equalsIgnoreCase("logout")) {
            HttpSession session = request.getSession();
            session.removeAttribute("id");
            session.removeAttribute("level");
            return "redirect:/login";
        }

        Account accountDto = Account.builder()
                .userid(null)
                .password("")
                .name(null)
                .email(null)
                .affiliation(null)
                .level(1)
                .build();

        model.addAttribute("accountDto", accountDto);
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @Valid Account accountDto, BindingResult result,
            HttpServletRequest request) {

        if (result.hasErrors()) {
            logger.warn("유효성 검사에서 탈락");
            for (ObjectError error : result.getAllErrors()) {
                logger.warn("유효성 실패 원인: " + error.getDefaultMessage());
            }
            model.addAttribute("accountDto", accountDto);
            return "login";
        }
        Account account = accountService.findByEmailAndPassword(accountDto.getEmail(),
                accountDto.getPassword());

        if (account == null) {
            logger.warn("존재하지 않는 계정");
            model.addAttribute("accountDto", accountDto);
            return "login";
        }
        // 세션에 id(PK) 저장
        HttpSession session = request.getSession();
        session.setAttribute("id", account.getId());
        session.setAttribute("level", account.getLevel());

        // 세션에 등록된 id를 로그에 출력
        logger.info("세션에 등록된 id: " + session.getAttribute("id"));

        return "redirect:/";
    }

}
