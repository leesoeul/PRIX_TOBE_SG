package com.prix.homepage.livesearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LivesearchController {

  @GetMapping("/livesearch")
  public String gotoLivesearchPage() {
      return "livesearch";
  }

  @GetMapping("/modplus/search")
  public String gotoModplusSearchPage() {
      return "modplus/search";
  }
}
