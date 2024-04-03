package com.prix.homepage.download.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/download")
public class DownloadController {

  @GetMapping("")
  public String gotoDownloadPage() {
      return "download";
  }

  @GetMapping("/modplus")
  public String gotoModplusPage() {
      return "download/modplus";
  }

}
