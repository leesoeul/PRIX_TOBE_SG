package com.prix.homepage.user.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prix.homepage.user.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class DeleteController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final AccountService accountService;

  @GetMapping("/deleteAccount")
  public String gotoDeleteAccountPage() {
    return "deleteAccount";
  }

  @PostMapping("/deleteAccount")
  public String deleteAccount(Model model, @RequestParam(required = true) Map<String, String> paramsMap,
      HttpServletRequest request) {
  
    if (paramsMap.containsKey("action") && paramsMap.get("action").equalsIgnoreCase("delete")) {
      HttpSession session = request.getSession();
      Integer idObject = (Integer) session.getAttribute("id");
  
      //session에 "id"가 존재하면
      if (idObject != null) {
        int id = idObject;
  
        logger.debug("삭제할 계정의 id : {}", id);
        
        try{
          accountService.deleteAccount(id);
        } catch (Exception e) {
          logger.error("삭제 실패 id : {}", id);
          return "redirect:/deleteAccount";
        }
  
        session.removeAttribute("id");
        session.removeAttribute("level");
      } else {
        logger.warn("세션에 'id' 속성이 없습니다.");

        return "redirect:/deleteAccount";
      }
    }
    return "redirect:/";
  }
  
}
