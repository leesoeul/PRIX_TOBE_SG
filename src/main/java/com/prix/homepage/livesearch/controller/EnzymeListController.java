package com.prix.homepage.livesearch.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prix.homepage.user.pojo.Enzyme;
import com.prix.homepage.user.service.EnzymeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

/**
 * url = prix.hanyang.ac.kr/modplus/enzyme_list
 * prix.hanyang.ac.kr/modplus/search 페이지에서 enzyme + 버튼 클릭시 뜨는 팝업창
 */
@Controller
@AllArgsConstructor
public class EnzymeListController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final EnzymeService enzymeService;

  /**
   * /livesearch/modplus에서 enzyme rule 추가 버튼 클릭시 뜨는 팝업창
   * url = /modplus/enzyme_list
   * 
   * @param request session 에서 id 받는 용도
   */
  @GetMapping("/modplus/enzyme_list")
  public String modplusEnzymeList(Model model, HttpServletRequest request) {
    // 세션에서 id 가져오기
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    int addState = 0;

    // select * from px_enzyme where user_id=" + id
    List<Enzyme> listEnzyme = enzymeService.getAllEnzymeByUserId(id);

    model.addAttribute("id", id);
    model.addAttribute("addState", addState);
    model.addAttribute("listEnzyme", listEnzyme);

    return "livesearch/enzyme_list";
  }

  /**
   * /modplus/enzyme_list에서 add 클릭하거나 delete 클릭시
   * 
   * @param paramsMap /modplus/enzyme_list?action=add 이거나 del
   * @param request   session에서 id 받는 용도
   */
  @PostMapping("/modplus/enzyme_list")
  public String addEnzymeList(Model model, @RequestParam Map<String, String> paramsMap,
      HttpServletRequest request) {
    // 세션에서 id 가져오기
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    // action param 가져오기
    String action = paramsMap.get("action");
    int addState = 0;

    if (id != null && action != null) {
      // action이 add이면 enzyme추가
      if (action.compareTo("add") == 0) {
        if (paramsMap.get("enzyme_name") == "") {
          addState = 1;
        } else if (paramsMap.get("enzyme_name").contains("|")) {
          addState = 5;
        } else {
          if (paramsMap.get("nt_cleave") == "" && paramsMap.get("ct_cleave") == "") {
            addState = 2;
          } else {
            String cleaveStr = paramsMap.get("ct_cleave");
            for (int c = 0; c < cleaveStr.length(); c++) {
              if (!Character.isLetter(cleaveStr.charAt(c))) {
                addState = 3;
                break;
              }
            }
            cleaveStr = request.getParameter("nt_cleave");
            for (int c = 0; c < cleaveStr.length(); c++) {
              if (!Character.isLetter(cleaveStr.charAt(c))) {
                addState = 3;
                break;
              }
            }
          }
        }
        if (addState == 0) {
          String enzymeName = paramsMap.get("enzyme_name").replace("'", "\\\'");
          Integer existedId = enzymeService.selectIdByUserIdAndName(id, enzymeName);
          if (existedId != null) {
            addState = 4;
          } else {
            String ntCleave = paramsMap.get("nt_cleave").toUpperCase();
            String ctCleave = paramsMap.get("ct_cleave").toUpperCase();
            try {
              enzymeService.insertEnzyme(id, enzymeName, ntCleave, ctCleave);
            } catch (Exception e) {
              logger.error("Error inserting px_enzyme for ID {}: {}", id, e.getMessage());
            }
          }
        }
      }
      // action이 del이면 enzyme 삭제
      else if (action.compareTo("del") == 0) {
        try {
          Integer enzymeId = Integer.parseInt(paramsMap.get("id"));
          enzymeService.deleteEnzyme(enzymeId, id);
        } catch (Exception e) {
          logger.error("Error deleting px_enzyme for ID {}: {}", id, e.getMessage());
        }
      }
    }

    // select * from px_enzyme where user_id=" + id
    List<Enzyme> listEnzyme = enzymeService.getAllEnzymeByUserId(id);

    model.addAttribute("id", id);
    model.addAttribute("addState", addState);
    model.addAttribute("listEnzyme", listEnzyme);

    return "livesearch/enzyme_list";
  }
}
