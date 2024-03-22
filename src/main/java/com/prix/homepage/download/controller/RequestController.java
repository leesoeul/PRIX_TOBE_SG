package com.prix.homepage.download.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestController {
  
  @GetMapping("/request")
  public String gotoRequestSoftwaredPage(@RequestParam String software , Model model) {
      model.addAttribute("software", software);
      return "request";
  }
}
