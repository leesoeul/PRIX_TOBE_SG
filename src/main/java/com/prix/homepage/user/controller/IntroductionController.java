package com.prix.homepage.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IntroductionController {
  
  @GetMapping("/")
  public String gotoIntroductionPage() {
      return "index";
  }

  @GetMapping("/help")
  public String gotoHelpPage() {
      return "help";
  }

  @GetMapping("/contact")
  public String gotoContactPage() {
      return "contact";
  }
  
}
