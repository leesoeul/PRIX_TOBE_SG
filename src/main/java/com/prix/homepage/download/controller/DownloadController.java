package com.prix.homepage.download.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * prix.hanyang.ac.kr/download 페이지
 */
@Controller
@RequestMapping("/download")
public class DownloadController {

  /**
   * prix.hanyang.ac.kr/download로의 get request 매핑
   * 
   * @return download/download.html 렌더링
   */
  @GetMapping("")
  public String gotoDownloadPage() {
    return "/download/download";
  }

  /**
   * prix.hanyang.ac.kr/modplus로의 get request 매핑
   * 
   * @return download/modplus.html 렌더링
   */
  @GetMapping("/modplus")
  public String modplusPage() {
    return "download/modplus";
  }

  @GetMapping("/ACTG")
  public String actgPage() {
    return "download/ACTG";
  }

  @GetMapping("/cifter")
  public String cifterPage() {
    return "download/cifter";
  }

  @GetMapping("/dbond")
  public String dbondPage() {
    return "download/dbond";
  }

  @GetMapping("/demix")
  public String demixPage() {
    return "download/demix";
  }

  @GetMapping("/DDPSearch")
  public String ddpSearchPage() {
    return "download/DDPSearch";
  }

  @GetMapping("/nextsearch")
  public String nextSearchPage() {
    return "download/nextsearch";
  }

  @GetMapping("/moda")
  public String modaPage() {
    return "download/moda";
  }

  @GetMapping("/mutcombinator")
  public String mutcombinatorPage() {
    return "download/mutcombinator";
  }

  @GetMapping("/PatternMatchFrm")
  public String patternMatchPage() {
    return "download/PatternMatchFrm";
  }

  @GetMapping("/USE")
  public String USEPage() {
    return "download/USE";
  }
}
