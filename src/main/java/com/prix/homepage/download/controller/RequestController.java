package com.prix.homepage.download.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prix.homepage.download.Mailer;
import com.prix.homepage.download.pojo.SoftwareRequest;
import com.prix.homepage.download.service.RequestService;

/**
 * prix.hanyang.ac.kr/request?software=example 페이지
 * prix.hanyang.ac.kr/download/example에서 software download 클릭시 이동
 */
@Controller
public class RequestController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * prix.hanyang.ac.kr/reuqest?software= 로의 get reuqest 매핑
   * 
   * @param software download 요청한 소프트웨어 이름
   * @param model    download/request.html에서 소프트웨어명 전달 용도
   * @return download/request.html 렌더링
   */
  @GetMapping("/request")
  public String gotoRequestSoftwaredPage(@RequestParam(required = false) String software,
      Model model) {

    if (software == null || software.isEmpty())
      return "redirect:/publications";
    System.out.println(software);
    model.addAttribute("success", 0);
    model.addAttribute("software", software);
    return "download/request";
  }

  @Autowired
  private RequestService requestService;

  /**
   * Retrieve user information by id
   * 
   * @param userId : user id
   * @return download/request.html 렌더링
   **/
  @PostMapping("/request")
  public String submitRequest(
    // Model model, @RequestBody SoftwareRequest form, @RequestParam String agreement,
    //   @RequestParam String software) {
    // String name = form.getName();
    // String affiliation = form.getAffiliation();
    // String title = form.getTitle();
    // String email = form.getEmail();
    // String instrument = form.getInstrument();
    // // String software = form.getSoftware();

    Model model, @RequestParam String name, @RequestParam String affiliation,
    @RequestParam String title, @RequestParam String email,
    @RequestParam String instrument, @RequestParam String software,
    @RequestParam String agreement,
    RedirectAttributes redirectAttributes) {

    if (agreement == null)
      agreement = "0xno";
    int success = 0;

    if ("1xyes".compareTo(agreement) == 0 && email != null) {

      try {
        int sent = 0;
        // send email
        String subject = software + " request from " + name;
        Mailer mt = new Mailer();
        mt.sendEmailToMe(subject, name, affiliation, title, email, instrument);
        sent = 1;

        if (sent == 1) {
          System.out
              .println(name + ", " + affiliation + ", " + title + ", " + email + ", " + instrument + ", " + software);
          requestService.insert(name, affiliation, title, email, instrument, software);
          success = 1;
        }

      } catch (Exception e) {
        logger.error("Error inserting  : {}", e.getMessage());

        success = 2;
      }
    }

    model.addAttribute("success", success);
    model.addAttribute("software", software);
    model.addAttribute("email", email);

    return "download/request";
  }

}
