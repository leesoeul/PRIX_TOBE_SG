package com.prix.homepage.livesearch.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.prix.homepage.constants.PrixDataWriter;
import com.prix.homepage.constants.ProteinInfo;
import com.prix.homepage.livesearch.dao.DataMapper;
import com.prix.homepage.livesearch.pojo.ACTGProcessDto;
import com.prix.homepage.livesearch.pojo.ACTGResultDto;
import com.prix.homepage.livesearch.pojo.Data;
import com.prix.homepage.livesearch.pojo.DbondProcessDto;
import com.prix.homepage.livesearch.pojo.DbondResultDto;
import com.prix.homepage.livesearch.pojo.JobQueue;
import com.prix.homepage.livesearch.pojo.UserSettingDto;
import com.prix.homepage.livesearch.service.JobQueueService;
import com.prix.homepage.livesearch.service.PatternMatchService;
import com.prix.homepage.livesearch.service.UserModificationService;
import com.prix.homepage.livesearch.service.UserSettingService;
import com.prix.homepage.livesearch.service.impl.ACTGProcessService;
import com.prix.homepage.livesearch.service.impl.ACTGResultService;
import com.prix.homepage.livesearch.service.impl.DbondProcessService;
import com.prix.homepage.livesearch.service.impl.DbondResultService;
import com.prix.homepage.user.pojo.Account;
import com.prix.homepage.user.pojo.Database;
import com.prix.homepage.user.pojo.Enzyme;
import com.prix.homepage.user.pojo.SearchLogUser;
import com.prix.homepage.user.service.AccountService;
import com.prix.homepage.user.service.DatabaseService;
import com.prix.homepage.user.service.EnzymeService;
import com.prix.homepage.user.service.SearchLogUserService;

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
  private final DbondProcessService dbondProcessService;
  private final DataMapper dataMapper;
  private final DbondResultService dbondResultService;
  private final ACTGProcessService actgProcessService;
  private final ACTGResultService actgResultService;
  private final JobQueueService jobQueueService;
  private final SearchLogUserService searchLogUserService;

  private final PrixDataWriter prixDataWriter;
  private final PatternMatchService patternMatchService;

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
   * livesearch 카테고리에서 dbond
   * 
   * @param model
   * @param request 세션 및 param 확인 용도
   */
  @GetMapping("/dbond/dbond_search")
  public String gotoDbondSearchPage(Model model, HttpServletRequest request) {

    // 세션에서 id 확인, 없을시 anonymous용 4부여
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    final Integer anony = 4;
    if (id == null) {
      session.setAttribute("id", anony);
      id = (Integer) session.getAttribute("id");
    }

    // id와 일치하는 usersetting을 가져오거나 reuqest param에 따라 delete usermodification 수행
    UserSettingDto userSettingDto = userSettingService.getUsersetting(id, "dbond");

    if (request.getParameter("entry") == null) {
      try {
        // delete modification data for the anonymous user
        userModificationService.deleteByUserId(anony);
        logger.info("delete done in modplus search by anony");
      } catch (Exception e) {
        logger.error("Error deleting UserModification for ID {}: {}", anony, e.getMessage());
      }
    }

    String msIndex = request.getParameter("ms");
    String dbIndex = request.getParameter("db");
    String msFile = request.getParameter("msfile");
    String dbFile = request.getParameter("dbfile");
    String msType = request.getParameter("mstype");
    String inst = request.getParameter("inst");
    if (msType != null)
      userSettingDto.setDataFormat(msType.toLowerCase());
    if (inst != null)
      userSettingDto.setInstrument(inst.toUpperCase());
    String[] values = request.getParameterValues("protein_list");
    int dbNewIndex = -1;
    if (msIndex != null) {
      // in-depth dbond search
      // make new fasta file
      String fasta = "";
      String pwd = "";
      Data rsData = dataMapper.getNameContentById(id);
      if (rsData != null) {
        pwd = rsData.getName();
        File file = new File(pwd);
        try (InputStream is = new FileInputStream(file)) {
          InputStreamReader reader = new InputStreamReader(is);
          BufferedReader bReader = new BufferedReader(reader);
          String line = "";
          boolean include = false;
          while (true) {
            line = bReader.readLine();
            if (line == null)
              break;
            if (line.length() > 0 && line.charAt(0) == '>') {
              int last = line.indexOf(' ');
              String name = "";
              if (last < 0)
                name = line.substring(1);
              else
                name = line.substring(1, last);
              include = false;
              for (int i = 0; i < values.length; i++)
                if (name.compareTo(values[i]) == 0)
                  include = true;
              if (include)
                fasta += line + '\n';
            } else if (include) {
              fasta += line + '\n';
            }
          }
          bReader.close();
          reader.close();
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fasta.length() > 0) {
        try {
          dbNewIndex = prixDataWriter.write("fasta", dbFile, new ByteArrayInputStream(fasta.getBytes()));
        } catch (FileNotFoundException | UnsupportedEncodingException | SQLException e) {
          e.printStackTrace();
        }
      }
    }

    model.addAttribute("userSetting", userSettingDto);
    // 이후 modplus search에서 사용했던 코드 - 수정 필요한지 확인
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
    List<Enzyme>

    listEnzymeZeroResponseDto = enzymeService.getAllEnzymeByUserId(0);
    model.addAttribute("listEnzymeId0", listEnzymeZeroResponseDto);

    // px_enzyme : id, name where user_id = id
    List<Enzyme> listEnzymeResponseDto = enzymeService.getAllEnzymeByUserId(id);
    model.addAttribute("listEnzymeId", listEnzymeResponseDto);

    // dbond에 맞게 새로 추가한 것들
    model.addAttribute("userId", id);
    model.addAttribute("msIndex", msIndex);
    model.addAttribute("inst", inst);
    model.addAttribute("msType", msType);
    model.addAttribute("msFile", msFile);
    model.addAttribute("dbFile", dbFile);
    model.addAttribute("msIndex", msIndex);
    model.addAttribute("dbNewIndex", dbNewIndex);

    return "livesearch/dbond_search";
  }

  /**
   * dbond 페이지에서 submit할 경우, process 페이지로 이동한다.
   * 
   * @param request processService로 전달해서 processService에서 request를 통해 param과
   *                session에 접근한다
   */
  @PostMapping("/dbond/search")
  public String postModplusSearchPage(Model model, HttpServletRequest request,
      @RequestParam Map<String, String> paramsMap,
      @RequestParam("ms_file") MultipartFile msFile, @RequestParam("fasta") MultipartFile fasta) {
    // 세션에서 id 확인
    HttpSession session = request.getSession();
    String id = String.valueOf(session.getAttribute("id"));
    if (id == null)
      return "redirect:/login?url=modi/search";

    // 더미 processDto
    DbondProcessDto processDto = DbondProcessDto.builder()
        .finished(false).failed(true)
        .logPath("").xmlPath("").msPath("").dbPath("").decoyPath("")
        .title("").msIndex(0).dbIndex(0).multiPath("").engine("")
        .output("")
        .rate(0)
        .returnAddr("").build();

    // MultipartFile[] 배열로 변환
    MultipartFile[] multipartFiles = { msFile, fasta };

    try {
      processDto = dbondProcessService.process(id, request, paramsMap, multipartFiles);
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

    return "livesearch/dbond_process";
  }

  /**
   * process 완료시 result로 넘어감
   * 
   * @param request ResultService로 넘기고 ResultService에서 param 접근
   */
  @GetMapping("/dbond/result")
  public String getResultPage(Model model, HttpServletRequest request) {
    return handleResultPage(model, request);
  }

  @PostMapping("/dbond/result")
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
    DbondResultDto resultDto = DbondResultDto.builder()
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
        .max(0)
        .proteins(new ProteinInfo[0])
        .maxProteins(0)
        .level(1)
        .build();

    try {
      resultDto = dbondResultService.result(id, request, session);
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
    logger.info("Max: " + resultDto.getMax());
    logger.info("Proteins: " + resultDto.getProteins());
    logger.info("Max Proteins: " + resultDto.getMaxProteins());

    model.addAttribute("resultDto", resultDto);

    boolean notauthorized = true;
    if (resultDto.getUserId() != null) {
      notauthorized = !resultDto.getFileName().equals("390") && !resultDto.getFileName().equals("396")
          && resultDto.getLevel() <= 1
          && !id.equals(Integer.toString(resultDto.getUserId()));
    }

    model.addAttribute("notauthorized", notauthorized);
    return "livesearch/dbond_result";
  }

  /**
   * ACTG 화면
   */
  @GetMapping("/ACTG/search")
  public String getACTG(Model model, HttpServletRequest request) {
    // 세션에서 id 확인, 없을시 anonymous용 4부여
    HttpSession session = request.getSession();
    Integer id = (Integer) session.getAttribute("id");
    final Integer anony = 4;
    if (id == null) {
      session.setAttribute("id", anony);
      id = (Integer) session.getAttribute("id");
    }

    Account userAccount = accountService.getAccount(id);
    String username = "anonymous";
    if (userAccount != null) {
      username = userAccount.getName();
    }
    model.addAttribute("username", username);

    return "livesearch/actg";
  }

  /**
   * ACTG 화면에서 클릭시 help로 이동
   * ex) /ACTG/help#PEP
   */
  @GetMapping("/ACTG/help")
  public String getHelp() {
    return "livesearch/help";
  }

  /**
   * ACTG process에서 rate 확인 목적으로 windows.location 변경할때 접속되는 용도
   * 
   * @param paramsMap /*[[@{/process(execute='no', process=${processName},
   *                  title=${title})}]]
   */
  @GetMapping("/ACTG/process")
  public String getACTG(Model model, HttpServletRequest request, @RequestParam Map<String, String> paramsMap) {
    // 세션에서 id 확인
    HttpSession session = request.getSession();
    String id = String.valueOf(session.getAttribute("id"));
    if (id == null) {
      return "redirect:/login?url=ACTG/search";
    }

    // dummy process dto
    ACTGProcessDto processDto = ACTGProcessDto.builder()
        .rate(0)
        .failed(false)
        .finished(false)
        .output("")
        .title("")
        .processName("")
        .build();

    try {
      // GET 요청에서는 파일을 업로드할 수 없기 때문에 파일을 제외하고 처리
      processDto = actgProcessService.process(id, request, paramsMap, null);
    } catch (Exception e) {
      logger.error("process service error: {}", e.getMessage());
      e.printStackTrace();
    }

    model.addAttribute("processDto", processDto);

    return "livesearch/actg_process";
  }

  /**
   * actg에서 submit시 진행과정 보여주는 process 화면
   * 
   * @param paramsMap    form 으로 제출 받은 input들
   * @param peptideFile  type이 file인 pepetide file
   * @param mutationFile type이 file인 mutation file
   */
  @PostMapping("/ACTG/process")
  public String postACTG(Model model, HttpServletRequest request, @RequestParam Map<String, String> paramsMap,
      @RequestParam("peptideFile") MultipartFile peptideFile,
      @RequestParam("mutationFile") MultipartFile mutationFile) {
    // 세션에서 id 확인
    HttpSession session = request.getSession();
    String id = String.valueOf(session.getAttribute("id"));
    if (id == null)
      return "redirect:/login?url=ACTG/search";

    // dummy process dto
    ACTGProcessDto processDto = ACTGProcessDto.builder().rate(0).failed(false).finished(false)
        .output("").title("").processName("").build();
    // MultipartFile[] 배열로 변환
    MultipartFile[] multipartFiles = { peptideFile, mutationFile };
    try {
      processDto = actgProcessService.process(id, request, paramsMap, multipartFiles);
    } catch (Exception e) {
      logger.error("process service error: {}", e.getMessage());
      e.printStackTrace();
    }
    if (processDto.isFinished()) {// 정상종료시 result페이지로 이동
      return "redirect:/ACTG/result?index=" + processDto.getPrixIndex();
    }

    model.addAttribute("processDto", processDto);

    return "livesearch/actg_process";
  }

  /**
   * process 완료시 result로 넘어감
   * 
   * @param request ResultService로 넘기고 ResultService에서 param 접근
   */
  @GetMapping("/ACTG/result")
  public String ACTGResultPage(Model model, HttpServletRequest request, HttpSession session) {
    ACTGResultDto actgResultDto = actgResultService.result(request, session);

    model.addAttribute("resultDto", actgResultDto);
    return "livesearch/actg_result";
  }

  @GetMapping("/history")
  public String history(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession();
    Integer userId = (Integer) session.getAttribute("id");
    Object idObject = session.getAttribute("id");
    if (idObject == null) {
      return "redirect:/login";
    }

    List<JobQueue> jobQueueDto = jobQueueService.selectJCandTitle(userId);
    List<SearchLogUser> searchLogUsersDto = searchLogUserService.findByUserId(userId);
    // Map 형태로 name, msfile, db의 이름 가져오기
    Map<Integer, String> msFiles = new HashMap<>();
    for (SearchLogUser searchLog : searchLogUsersDto) {
      Integer id = searchLog.getMsfile();
      String fileName = searchLogUserService.findFile(id);
      msFiles.put(id, fileName);
    }
    Map<Integer, String> dbNames = new HashMap<>();
    for (SearchLogUser searchLog : searchLogUsersDto) {
      Integer id = searchLog.getDb();
      String fileName = searchLogUserService.findFile(id);
      dbNames.put(id, fileName);
    }

    model.addAttribute("jobQueueDto", jobQueueDto);
    model.addAttribute("searchLogUsersDto", searchLogUsersDto);
    model.addAttribute("msFiles", msFiles);
    model.addAttribute("dbNames", dbNames);

    return "livesearch/history";
  }

  @GetMapping("/historyModi")
  public String historyModi(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession();
    Integer userId = (Integer) session.getAttribute("id");
    Object idObject = session.getAttribute("id");
    if (idObject == null) {
      return "redirect:/login";
    }

    List<SearchLogUser> searchLogUsersDto = searchLogUserService.findByUserId(userId);
    // Map 형태로 name, msfile, db의 이름 가져오기
    Map<Integer, String> msFiles = new HashMap<>();
    for (SearchLogUser searchLog : searchLogUsersDto) {
      Integer id = searchLog.getMsfile();
      String fileName = searchLogUserService.findFile(id);
      msFiles.put(id, fileName);
    }
    Map<Integer, String> dbNames = new HashMap<>();
    for (SearchLogUser searchLog : searchLogUsersDto) {
      Integer id = searchLog.getDb();
      String fileName = searchLogUserService.findFile(id);
      dbNames.put(id, fileName);
    }

    model.addAttribute("searchLogUsersDto", searchLogUsersDto);
    model.addAttribute("msFiles", msFiles);
    model.addAttribute("dbNames", dbNames);

    return "livesearch/historyModi";
  }
}
