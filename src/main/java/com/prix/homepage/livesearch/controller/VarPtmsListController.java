package com.prix.homepage.livesearch.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prix.homepage.livesearch.pojo.Modification;
import com.prix.homepage.livesearch.service.UserModificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

/**
 * modplus, actg에서 Variable modification list룰 보여주는 팝업창
 * 예시: https://prix.hanyang.ac.kr/modplus/var_ptms_list?var=1&engine=0
 */
@Controller
@AllArgsConstructor
public class VarPtmsListController {
  private final UserModificationService userModificationService;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @GetMapping("/modplus/var_ptms_list")
  public String gotoModplusSearchPage(Model model, @RequestParam Map<String, String> paramsMap,
      HttpServletRequest request) {

    // 세션에서 id 확인 없으면 anonymous 4 부여. request param도 확인
    HttpSession session = request.getSession();
    final Integer anony = 4;
    Integer id = (Integer) session.getAttribute("id");
    String var = paramsMap.get("var");
    if (var == null)
      var = "1";
    String engine = paramsMap.get("engine");
    if (engine == null)
      engine = "0";
    String sortBy = paramsMap.get("sort");
    if (sortBy == null)
      sortBy = "";

    boolean reloadParant = false;
    if (id != null) {
      String[] values = request.getParameterValues("mod");
      if (values != null) {
        try {
          userModificationService.deleteByUserIdAndModId(id, engine, values);
        } catch (Exception e) {
          logger.error("Error deleting UserModification with mod_id for ID {} : {}", id, e.getMessage());
        }
        reloadParant = true;
      }
    }//여기까지 일단 jsp 초기 

    //id에 해당하는 usermodification을 바탕으로 modification 정보를 list로 받아냄(일단 userModificationService에 구현): var_ptms_list 170줄 주변
    List<Modification> listModification = userModificationService.findModListByUserMod(id, anony, id, sortBy);

    model.addAttribute("id", id);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("var", var);
    model.addAttribute("engine", engine);
    model.addAttribute("listModification", listModification);


    return "livesearch/var_ptms_list";
  }
}
