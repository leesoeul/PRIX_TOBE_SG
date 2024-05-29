package com.prix.homepage.livesearch.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.prix.homepage.constants.JobProcess;
import com.prix.homepage.constants.ProteinInfo;
import com.prix.homepage.livesearch.pojo.ProcessDto;
import com.prix.homepage.livesearch.pojo.ResultDto;
import com.prix.homepage.livesearch.pojo.UserSettingDto;
import com.prix.homepage.livesearch.service.UserModificationService;
import com.prix.homepage.livesearch.service.UserSettingService;
import com.prix.homepage.livesearch.service.impl.ProcessService;
import com.prix.homepage.livesearch.service.impl.ResultService;
import com.prix.homepage.user.pojo.Account;
import com.prix.homepage.user.pojo.Database;
import com.prix.homepage.user.pojo.Enzyme;
import com.prix.homepage.user.service.AccountService;
import com.prix.homepage.user.service.DatabaseService;
import com.prix.homepage.user.service.EnzymeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * prix.hanyang.ac.kr/livesearch 페이지
 */
@Controller
@AllArgsConstructor
public class LivesearchController {

  private final AccountService accountService;
  private final UserSettingService userSettingService;
  private final UserModificationService userModificationService;
  private final DatabaseService databaseService;
  private final EnzymeService enzymeService;
  private final ProcessService processService;
  private final ResultService resultService;

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

    // id와 일치하는 usersetting을 가져오거나 reuqest param에 따라 delete usermodification 수행
    UserSettingDto userSettingDto = userSettingService.getUsersettingById(id);
    // id에 해당하는 userSetting전달, 없으면 더미 전달
    model.addAttribute("userSetting", userSettingDto);

    if (request.getParameter("entry") == null) {
      try {
        // delete modification data for the anonymous user
        userModificationService.deleteByUserId(anony);
        logger.info("delete done in modplus search by anony");
      } catch (Exception e) {
        logger.error("Error deleting UserModification for ID {}: {}", anony, e.getMessage());
      }
    } else if (request.getParameter("act") != null) {
      String re_id = request.getParameter("act");
      try {
        userModificationService.deleteByUserIdVar0(re_id);
        logger.info("delete done in modplus search");
      } catch (Exception e) {
        logger.error("Error deleting UserModification with var0 for ID {}: {}", id, e.getMessage());
      }
    }

    Boolean engine = false;
    Boolean variable = true;
    Integer varModCount = userModificationService.countModifications(id, variable, engine);
    if (varModCount == null)
      varModCount = 0;
    variable = false;
    Integer fixedModCount = userModificationService.countModifications(id, variable, engine);
    if (fixedModCount == null)
      fixedModCount = 0;

    Account userAccount = accountService.getAccount(id);
    String username = "anonymous";
    if (userAccount != null) {
      username = userAccount.getName();
    }
    model.addAttribute("username", username);

    // Modifications 개수 전달
    model.addAttribute("varModCount", varModCount);
    model.addAttribute("fixedModCount", fixedModCount);

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

  /**
   * modplus페이지에서 submit할 경우, process 페이지로 이동한다.
   * 
   * @param request processService로 전달해서 processService에서 request를 통해 param과
   *                session에 접근한다
   */
  @PostMapping("/modplus/search")
  public String postModplusSearchPage(Model model, HttpServletRequest request,
      @RequestParam Map<String, String> paramsMap,
      @RequestParam("ms_file") MultipartFile msFile, @RequestParam("fasta") MultipartFile fasta) {
    // 세션에서 id 확인
    HttpSession session = request.getSession();
    String id = String.valueOf(session.getAttribute("id"));
    if (id == null)
      return "redirect:/login?url=modplus/search";

    //더미 processDto
    ProcessDto processDto = ProcessDto.builder()
        .finished(false).failed(true)
        .logPath("").xmlPath("").msPath("").dbPath("").decoyPath("")
        .title("").msIndex(0).dbIndex(0).multiPath("").engine("").jobCode("")
        .refreshCount(0).fileMsg("").output("")
        .rate(0)
        .returnAddr("").build();

    // MultipartFile[] 배열로 변환
    MultipartFile[] multipartFiles = { msFile, fasta };

    try {
      processDto = processService.process(id, request, paramsMap, multipartFiles);
    } catch (IOException e) {
      logger.error("process service error : {} ", e.getMessage());
      e.printStackTrace();
    }

    if (processDto.getReturnAddr().startsWith("redirect:/")) {
      return processDto.getReturnAddr();
    }

    // logger.info("ProcessDto Information:");
    // logger.info("Finished: " + processDto.getFinished());
    // logger.info("Failed: " + processDto.getFailed());
    // logger.info("Log Path: " + processDto.getLogPath());
    // logger.info("XML Path: " + processDto.getXmlPath());
    // logger.info("MS Path: " + processDto.getMsPath());
    // logger.info("DB Path: " + processDto.getDbPath());
    // logger.info("Decoy Path: " + processDto.getDecoyPath());
    // logger.info("Title: " + processDto.getTitle());
    // logger.info("MS Index: " + processDto.getMsIndex());
    // logger.info("DB Index: " + processDto.getDbIndex());
    // logger.info("Multi Path: " + processDto.getMultiPath());
    // logger.info("Engine: " + processDto.getEngine());
    // logger.info("Job Code: " + processDto.getJobCode());
    // logger.info("Refresh Count: " + processDto.getRefreshCount());
    // logger.info("File Message: " + processDto.getFileMsg());
    // logger.info("Output: " + processDto.getOutput());
    // logger.info("Rate: " + processDto.getRate());
    // logger.info("Return Address: " + processDto.getReturnAddr());

    model.addAttribute("processDto", processDto);

    // return processDto.getReturnAddr();

    return "livesearch/process";
  }

  /**
   * process 완료시 result로 넘어감
   * 
   * @param request ResultService로 넘기고 ResultService에서 param 접근
   */
  @GetMapping("/modplus/result")
  public String getResultPage(Model model, HttpServletRequest request) {
      return handleResultPage(model, request);
  }
  
  @PostMapping("/modplus/result")
  public String postResultPage(Model model, HttpServletRequest request) {
      return handleResultPage(model, request);
  }
  
  private String handleResultPage(Model model, HttpServletRequest request) {
      // 세션에서 id 확인 없으면 4(anony)로 지정
      final Integer anony = 4;
      HttpSession session = request.getSession();
      String id = String.valueOf(session.getAttribute("id"));
      if (id == null) {
          session.setAttribute("id", anony);
          id = (String) session.getAttribute("id");
      }
      ResultDto resultDto = ResultDto.builder()
          .summary(null)
          .mods(null)
          .fileName("")
          .isDBond(false)
          .useTargetDecoy(false)
          .minScore("")
          .maxHit("")
          .minFDR("")
          .sort("")
          .userId(null)
          .jobState(null)
          .max(0)
          .proteins(new ProteinInfo[0])
          .maxProteins(0)
          .level(1)
          .build();
  
      try {
          resultDto = resultService.result(id, request, session);
      } catch (Exception e) {
          logger.error("result service error: {}", e.getMessage());
          e.printStackTrace();
      }
  
      logger.info("ResultDto Information:");
      logger.info("Summary: " + resultDto.getSummary());
      logger.info("Mods: " + resultDto.getMods());
      logger.info("File Name: " + resultDto.getFileName());
      logger.info("Is DBond: " + resultDto.isDBond());
      logger.info("Use Target Decoy: " + resultDto.isUseTargetDecoy());
      logger.info("Min Score: " + resultDto.getMinScore());
      logger.info("Max Hit: " + resultDto.getMaxHit());
      logger.info("Min FDR: " + resultDto.getMinFDR());
      logger.info("Sort: " + resultDto.getSort());
      logger.info("User ID: " + resultDto.getUserId());
      logger.info("Job State: " + resultDto.getJobState());
      logger.info("Max: " + resultDto.getMax());
      logger.info("Proteins: " + resultDto.getProteins());
      logger.info("Max Proteins: " + resultDto.getMaxProteins());
  
      model.addAttribute("resultDto", resultDto);
      return "livesearch/result";
  }
  
  /**
   * process에서 취소 요청시 cancel 페이지 - 미완 추가 수정 필요 0528
   */
  @GetMapping("/modplus/search/cancel")
  public String getMethodName(Model model, @RequestParam("job") String jobCode, HttpServletRequest request) {
    HttpSession session = request.getSession();
    String id = String.valueOf(session.getAttribute("id"));
    if (id == null || jobCode == null)
      return "redirect:/login.jsp?url=modplus/search.jsp";

    JobProcess jobState = new JobProcess(jobCode, id);
    if (!jobState.isCorrectJob() || !jobState.isVaildUser(id))
      return "redirect:/login.jsp?url=modplus/search.jsp";

    boolean error = false;
    String errorMessage = null;
    try {
      String requestContents = jobState.sendRequest("stop");

      Runtime runtime = Runtime.getRuntime();
      String[] command = { "/bin/bash", "-c",
          String.format("%s %s",
              "java -cp /usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/WEB-INF/lib/Server.jar PrixUser",
              requestContents) };
      Process process = runtime.exec(command);

      if (!jobState.removeTraces()) {
        jobState.removeTraces();
      }
    } catch (Exception e) {
      error = true;
      errorMessage = jobState.getMessage();
    }

    if (error) {
      model.addAttribute("error", true);
      model.addAttribute("errorMessage", errorMessage);
    } else {
      model.addAttribute("error", false);
    }
    model.addAttribute("jobState", jobState);
    return "livesearch/cancel";
  }

}
