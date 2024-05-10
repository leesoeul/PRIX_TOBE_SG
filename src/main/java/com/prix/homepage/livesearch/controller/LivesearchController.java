package com.prix.homepage.livesearch.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.prix.homepage.livesearch.pojo.UserSettingDto;
import com.prix.homepage.livesearch.service.UserModificationService;
import com.prix.homepage.livesearch.service.UserSettingService;
import com.prix.homepage.user.pojo.Database;
import com.prix.homepage.user.pojo.Enzyme;
import com.prix.homepage.user.service.DatabaseService;
import com.prix.homepage.user.service.EnzymeService;

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
  private final DatabaseService databaseService;
  private final EnzymeService enzymeService;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * prix.hanyang.ac.kr/livesearch로의 get request 매핑
   * 
   * @return livesearch/livesearch.html 렌더링
   */
  @GetMapping("/livesearch")
  public String gotoLivesearchPage() {
    return "livesearch/livesearch";
  }

  /**
   * livesearch 카테고리에서 modplus
   * 
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

    // id에 해당하는 usersetting이 존재하지 않을시 보낼 dummy
    UserSettingDto userSettingDto = UserSettingDto.builder()
        .version("1.0.1")
        .enzyme(null)
        .missedCleavage(null)
        .minNumEnzTerm(null)
        .pTolerance("10")
        .minChar(null)
        .pUnit(null)
        .fTolerance("0.05")
        .minIE(null)
        .maxIE(null)
        .minMM("-150")
        .maxMM("+250")
        .dataFormat(null)
        .instrument(null)
        .msResolution(null)
        .msmsResolution(null)
        .build();

    // id와 일치하는 usersetting을 가져오거나 reuqest param에 따라 delete usermodification 수행
    if (id.compareTo(anony) != 0) {
      userSettingDto = userSettingService.getUsersettingById(id);
    } else if (request.getParameter("entry") == null) {
      try {
        // delete modification data for the anonymous user
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

    // id에 해당하는 userSetting전달, 없으면 더미 전달
    model.addAttribute("userSetting", userSettingDto);

    // px_database : id, name, file # 비었으면 빈 List []
    List<Database> listDatabaseResponseDto = databaseService.getAllDatabase();
    model.addAttribute("listDatabase", listDatabaseResponseDto);

    // px_enzyme : id, name where user_id = 0
    List<Enzyme> listEnzymeZeroResponseDto = enzymeService.getAllEnzymeByUserId(0);
    model.addAttribute("listEnzymeId0", listEnzymeZeroResponseDto);

    // px_enzyme : id, name where user_id = id
    List<Enzyme> listEnzymeResponseDto = enzymeService.getAllEnzymeByUserId(id);
    model.addAttribute("listEnzymeId", listEnzymeResponseDto);

    return "livesearch/modplus";
  }



}
