package com.prix.homepage.publications.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publications")
public class PublicationsController {

  @GetMapping("")
  public String gotoPublicationsPage() {
      return "publications";
  }

}
