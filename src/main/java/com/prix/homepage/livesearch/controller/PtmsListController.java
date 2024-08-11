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

import com.prix.homepage.livesearch.pojo.Modification;
import com.prix.homepage.livesearch.service.ModificationService;
import com.prix.homepage.livesearch.service.UserModificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

/**
 * dbond에서 Variable modification list룰 보여주는 팝업창
 * 예시: https://prix.hanyang.ac.kr/dbond/var_ptms_list?var=1&engine=0
 */
@Controller
@AllArgsConstructor
public class PtmsListController {
  private final UserModificationService userModificationService;
  private final ModificationService modificationService;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * dbond/dbond_search에서 variable modifications의 + 버튼 클릭시 뜨는 팝업창
   * @param paramsMap id, var, engine, sort
   * @param request 세션 접근 용도
   */
  @GetMapping("/dbond/var_ptms_list")
  public String varPtmsListPopUp(Model model, @RequestParam Map<String, String> paramsMap,
      HttpServletRequest request) {

    // 세션에서 id 확인 없으면 anonymous 4 부여. request param도 확인
    HttpSession session = request.getSession();
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
    Boolean varBool = var.equals("1");
    Boolean engineBool = engine.equals("1");

    //id에 해당하는 usermodification을 바탕으로 modification 정보를 list로 받아냄
    List<Modification> listModification = modificationService.findModListByUserMod(id, varBool, engineBool, sortBy);

    model.addAttribute("id", id);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("var", var);
    model.addAttribute("engine", engine);
    model.addAttribute("listModification", listModification);

    return "livesearch/var_ptms_list";
  }

  /**
   * var_ptms_list팝업창에서 delete 요청
   * @param paramsMap i
   * @param request
   * @return
   */
  @PostMapping("/dbond/var_ptms_list")
  public String postVarPtmsListPopUp(Model model, @RequestParam Map<String, String> paramsMap,
      HttpServletRequest request) {

    // 세션에서 id 확인 없으면 anonymous 4 부여. request param도 확인
    HttpSession session = request.getSession();
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
    Boolean varBool = var.equals("1");
    Boolean engineBool = engine.equals("1");

    if (id != null) {
      String[] modIds = request.getParameterValues("mod");
        try {
          userModificationService.deleteByUserIdAndModId(id, engineBool, modIds);
          logger.info("delete done in var_ptms_list");
        } catch (Exception e) {
          logger.error("Error deleting UserModification with mod_id for ID {} : {}", id, e.getMessage());
        }
      }
    //id에 해당하는 usermodification을 바탕으로 modification 정보를 list로 받아냄
    List<Modification> listModification = modificationService.findModListByUserMod(id, varBool, engineBool, sortBy);

    model.addAttribute("id", id);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("var", var);
    model.addAttribute("engine", engine);
    model.addAttribute("listModification", listModification);

    return "livesearch/var_ptms_list";
  }

  /**
   *  var_ptms_list 팝업창에서 Add form User 클릭시 뜨는 팝업창
   * @param paramsMap var, engine, act request parameter
   * @param request 세션 접근 용도
   */
  @GetMapping("/dbond/user_ptms_list")
  public String userPtmsListPopUp(Model model, @RequestParam Map<String, String> paramsMap,
      HttpServletRequest request) {

    // 세션에서 id 확인. request param도 확인
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    String var = paramsMap.get("var");
    if (var == null)
      var = "1";
    String engine = paramsMap.get("engine");
    if (engine == null)
      engine = "0";
    String action = paramsMap.get("act");
    if (action == null)
      action = "";

    int addState = 0;


    //user modification에 없는 modification의 list
    Boolean engineBool = engine.equals("1");
    // logger.info("engineBool : {}, engine: {}", engineBool, engine);

  // modificationService에서 반환된 값을 ArrayList에 할당
  List<Modification> listMod = modificationService.selectModListNotInUserMod(id, var, engineBool);


    model.addAttribute("id", id);
    model.addAttribute("var", var);
    model.addAttribute("engine", engine);
    model.addAttribute("addState", addState);
    model.addAttribute("listMod", listMod);


    return "livesearch/user_ptms_list";
  }


  /**
   * user_ptms_list 팝업창에서 post 시도시 처리
   * @param paramsMap var, engine, act request parameter
   * @param request session 접근 용도
   */
  @PostMapping("/dbond/user_ptms_list")
  public String postUserPtmsListPopUp(Model model, @RequestParam Map<String, String> paramsMap,
      HttpServletRequest request) {

    // 세션에서 id 확인. request param도 확인
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    String var = paramsMap.get("var");
    if (var == null)
      var = "1";
    String engine = paramsMap.get("engine");
    if (engine == null)
      engine = "0";
    String action = paramsMap.get("act");
    if (action == null)
      action = "";

    int addState = 0;
    boolean finished = false;
    if (id != null) {
      //action이 add이면 modification에 추가한 내역을 저장한다
      if(action.compareTo("add")==0)
      {
        if(paramsMap.get("name") == ""){
          addState = 1;
        }else if(paramsMap.get("name").contains("|") || paramsMap.get("name").contains(" ")){
          addState = 5;
        }else{
          if(paramsMap.get("mass") == ""){
            addState = 2;
          }else{
            try {
              Double.parseDouble( paramsMap.get("mass") );
              if( paramsMap.get("residue").equals("N-term") ){
                if( paramsMap.get("position").equals("ANYWHERE") || paramsMap.get("position").endsWith("C_TERM") )
                  addState= 4;
              }
              else if( paramsMap.get("residue").equals("C-term") ){
                if( paramsMap.get("position").equals("ANYWHERE") || paramsMap.get("position").endsWith("N_TERM") )
                  addState= 4;
              }
            } catch (NumberFormatException e) {
              addState= 3;
            }			
          }
        }
        if(addState == 0){
          String position = paramsMap.get("position");
          String name = paramsMap.get("name");
          Double massDiff = Double.parseDouble(paramsMap.get("mass"));
          String residue = paramsMap.get("residue");

          try{
            modificationService.insert(id, name, massDiff, residue, position);
          }catch(Exception e){
            logger.error("Error inserting Modification : {}", e.getMessage());
          }
        }
      }
      //action이 submit이면 userModification에 해당 내역을 저장한다
      else if(action.compareTo("submit") == 0)
      {
        String[] modIds = request.getParameterValues("mod");
        Boolean varBool = var.equals("1");
        Boolean engineBool = engine.equals("1");

        try{
          userModificationService.insertWithModIds(id, modIds, varBool, engineBool);
        }catch(Exception e){
          logger.error("Error inserting UserModification : {}", e.getMessage());
        }
        finished = true;
      }
      else if(action.length() > 0){
        try{
          Integer actionInt = Integer.parseInt(action);
          modificationService.deleteById(actionInt);
        }catch(Exception e){
          logger.error("Error delete Modification : {}", e.getMessage());
        }
      }
    }//여기까지 일단 jsp 초기 


    //user modification에 없는 modification의 list
    Boolean engineBool = engine.equals("1");
    List<Modification> listMod= modificationService.selectModListNotInUserMod(id, var, engineBool);

    model.addAttribute("finished", finished);
    model.addAttribute("id", id);
    model.addAttribute("var", var);
    model.addAttribute("engine", engine);
    model.addAttribute("addState", addState);
    model.addAttribute("listMod", listMod);


    return "livesearch/user_ptms_list";
  }


  /**
   * unimod_ptms_list에서 사용하는 ModFinder 클래스
   */
  class ModFinder {
    public ModFinder(String[] values)
    {
      modValues = values;
    }
    public boolean findMod(String mod)
    {
      if (modValues == null)
        return false;
      for (int i = 0; i < modValues.length; i++)
        if (mod.compareTo(modValues[i]) == 0)
          return true;
      return false;
    }
    private String[] modValues;
  }

  /**
   *  var_ptms_list 팝업창에서 Add form Unimod 클릭시 뜨는 팝업창
   * @param paramsMap var, engine, act request parameter
   * @param request 세션 접근 용도
   */
  @GetMapping("/dbond/unimod_ptms_list")
  public String unimodPtmsListPopUp(Model model, @RequestParam Map<String, String> paramsMap,
      HttpServletRequest request) {

    // 세션에서 id 확인. request param도 확인
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    String var = paramsMap.get("var");
    if (var == null)
      var = "1";
    String engine = paramsMap.get("engine");
    if (engine == null)
      engine = "0";
    String topClass = paramsMap.get("top");
    int top = 1;
    if(topClass != null)
      top = Integer.parseInt(topClass);
    String sortBy = paramsMap.get("sort");
    String filter = paramsMap.get("filter");
    if(filter == null)
      filter = "default";
    
    int max = 0;
    boolean finished = false;
    String[] modValues = request.getParameterValues("mod");

    if(sortBy == null) sortBy = "name asc";

    ModFinder modFinder = new ModFinder(modValues);
    Integer engineBit = Integer.parseInt(engine);

    logger.info("id: {}, var: {}, engineBit: {}, filter: {}, sortBy: {}", id, var, engineBit, filter, sortBy);

    Boolean varBool = var.equals("1");
    List<Modification> listModJoinClass= modificationService.selectModJoinClass(id, varBool, engineBit, filter, sortBy);

    model.addAttribute("finished", finished);//var_ptms_list 업데이트 용도
    model.addAttribute("id", id);
    model.addAttribute("var", var);
    model.addAttribute("engine", engine);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("filter", filter);
    model.addAttribute("modFinder", modFinder);
    model.addAttribute("listModJoinClass", listModJoinClass);

    return "livesearch/unimod_ptms_list";
  }

  /**
   *  var_ptms_list 팝업창에서 Add form Unimod 클릭시 뜨는 팝업창
   * @param paramsMap var, engine, act request parameter
   * @param request 세션 접근 용도
   */
  @PostMapping("/dbond/unimod_ptms_list")
  public String postUnimodPtmsListPopUp(Model model, @RequestParam Map<String, String> paramsMap,
      HttpServletRequest request) {

    // 세션에서 id 확인. request param도 확인
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    String var = paramsMap.get("var");
    if (var == null)
      var = "1";
    String engine = paramsMap.get("engine");
    if (engine == null)
      engine = "0";
    String topClass = paramsMap.get("top");
    int top = 1;
    if(topClass != null)
      top = Integer.parseInt(topClass);
    String sortBy = paramsMap.get("sort");
    String filter = paramsMap.get("filter");
    if(filter == null)
      filter = "default";
    
    int max = 0;
    boolean finished = false;
    String[] modIds = request.getParameterValues("mod");

    if(id != null)
    {
      if(request.getParameter("submit") != null && modIds != null)
      {
        if (modIds != null && modIds.length > 0) {
          try {
            Boolean varBool = var.equals("1");
            Boolean engineBool = engine.equals("1");
              userModificationService.insertWithModIds(id, modIds,varBool , engineBool);
              logger.info("insert done");
              finished = true;
          } catch (Exception e) {
            logger.error("Error insert Modification : {}", e.getMessage());
          }
        }
      }
    }

    if(sortBy == null) sortBy = "name asc";

    ModFinder modFinder = new ModFinder(modIds);

    Integer engineBit = Integer.parseInt(engine);
    Boolean varBool = var.equals("1");

    List<Modification> listModJoinClass= modificationService.selectModJoinClass(id, varBool, engineBit, filter, sortBy);

    model.addAttribute("finished", finished);//var_ptms_list 업데이트 용도
    model.addAttribute("id", id);
    model.addAttribute("var", var);
    model.addAttribute("engine", engine);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("filter", filter);
    model.addAttribute("modFinder", modFinder);
    model.addAttribute("listModJoinClass", listModJoinClass);

    return "livesearch/unimod_ptms_list";
  }

}
