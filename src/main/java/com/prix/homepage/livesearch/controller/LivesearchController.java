package com.prix.homepage.livesearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.prix.homepage.livesearch.pojo.UserSettingDto;
import com.prix.homepage.livesearch.service.UserModificationService;
import com.prix.homepage.livesearch.service.UserSettingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

/**
 * prix.hanyang.ac.kr/livesearch 페이지
 */
@Controller
@AllArgsConstructor
public class LivesearchController {

  private final UserSettingService userSettingService;
  private final UserModificationService userModificationService;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * prix.hanyang.ac.kr/livesearch로의 get request 매핑
   * 
   * @return livesearch/livesearch.html 렌더링
   */
  @GetMapping("/livesearch")
  public String gotoLivesearchPage() {
    return "/livesearch/livesearch";
  }

  /**
   * livesearch 카테고리에서 modplus
   * @param model
   * @param request 세션 및 param 확인 용도
   */
  @GetMapping("/modplus/search")
  public String gotoModplusSearchPage(Model model, HttpServletRequest request) {

    // 세션에서 id 확인, 없을시 anonymous용 4부여
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    final Integer anony = 4;
    if (id == null) {
      session.setAttribute("id", anony);
      id = (Integer) session.getAttribute("id");
    }

    //id에 해당하는 usersetting이 존재하지 않을시 보낼 dummy
    UserSettingDto userSettingDto = UserSettingDto.builder()
        .version("")
        .enzyme(null)
        .missedCleavage(null)
        .minNumEnzTerm(null)
        .pTolerance(null)
        .minChar(null)
        .pUnit(null)
        .fTolerance(null)
        .minIE(null)
        .maxIE(null)
        .minMM(null)
        .maxMM(null)
        .dataFormat(null)
        .instrument(null)
        .msResolution(null)
        .msmsResolution(null)
        .build();

    //id와 일치하는 usersetting을 가져오거나 reuqest param에 따라 delete usermodification 수행
    if (id.compareTo(anony) != 0) {
      userSettingDto = userSettingService.getUsersettingById(id);
    } else if (request.getParameter("entry") == null) {
      try {
        userModificationService.deleteByUserId(id);
      } catch (Exception e) {
        logger.error("Error deleting UserModification for ID {}: {}", id, e.getMessage());
      }
    } else if (request.getParameter("act") != null) {
      String re_id = request.getParameter("act");
      try {
        userModificationService.deleteByUserIdVar0(re_id);
      } catch (Exception e) {
        logger.error("Error deleting UserModification with var0 for ID {}: {}", id, e.getMessage());
      }
    }

    model.addAttribute("userSetting", userSettingDto);

    return "/livesearch/modplus";
  }
}
