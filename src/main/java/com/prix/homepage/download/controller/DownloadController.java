package com.prix.homepage.download.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/download")
public class DownloadController {

  @GetMapping("")
  public String gotoDownloadPage(Model model, HttpServletRequest request) {
    // 현재 요청의 URL을 가져옴
    String currentUrl = request.getRequestURL().toString();
    model.addAttribute("currentUrl", currentUrl);

    return "/download/download";
  }

  @GetMapping("/modplus")
  public String gotoModplusPage(HttpServletRequest request) {
    return "download/modplus";
  }

}
