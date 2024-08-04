package com.prix.homepage.download.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prix.homepage.download.Mailer;
import com.prix.homepage.download.service.SoftwareRequestService;

import lombok.AllArgsConstructor;

/**
 * prix.hanyang.ac.kr/software_request?software=[parameter] 페이지
 * prix.hanyang.ac.kr/download/[parameter]에서 software download 클릭시 이동
 * 요청한 software를 db에 저장 후 메일로 발송하는 controller
 */
@Controller
@AllArgsConstructor
public class SoftwareRequestController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final SoftwareRequestService softwareRequestService;

  /**
   * prix.hanyang.ac.kr/reuqest?software= 로의 get reuqest 매핑
   * 
   * @param software download 요청한 소프트웨어 이름
   * @param model    download/software_request.html에서 소프트웨어명 전달 용도
   * @return download/software_request.html 렌더링
   */
  @GetMapping("/software_request")
  public String gotoRequestSoftwaredPage(@RequestParam(required = false) String software,
      Model model) {

    if (software == null || software.isEmpty())
      return "redirect:/publications";
    model.addAttribute("success", 0);
    model.addAttribute("software", software);
    return "download/software_request";
  }

  /**
   * Retrieve user information by id
   * 
   * @return download/software_request.html 렌더링
   *         software request 메일 발송 성공 여부를 반환한다.
   **/
  @PostMapping("/download/software_request")
  public String submitRequest(
      Model model,
      @RequestParam String name,
      @RequestParam String affiliation,
      @RequestParam String title,
      @RequestParam String email,
      @RequestParam String instrument,
      @RequestParam String software,
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
          softwareRequestService.insert(name, affiliation, title, email, instrument, software);
          success = 1; // 메일 전송이 성공한 경우
        }

      } catch (Exception e) {
        logger.error("Error inserting request: {}", e.getMessage(), e);

        success = 2; // 메일 전송이 실패한
      }
    }

    model.addAttribute("success", success);
    model.addAttribute("software", software);
    model.addAttribute("email", email);

    return "download/software_request";
  }

}
