package com.prix.homepage.livesearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prix.homepage.configuration.GlobalProperties;
import com.prix.homepage.livesearch.dao.DataMapper;
import com.prix.homepage.livesearch.dao.SearchLogMapper;
import com.prix.homepage.livesearch.pojo.SearchLog;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * actg result 페이지에서 결과 파일 다운로드 클릭시 호출
 */
@RestController
public class FileDownloadController {

  @Autowired
  private SearchLogMapper searchLogMapper;

  @Autowired
  private DataMapper dataMapper;

  @Autowired
  private GlobalProperties globalProperties;

  //Deprecated
  @GetMapping("/ACTG/downloads")
  public void downloadFile(@RequestParam("index") String index, HttpServletResponse response, HttpSession session)
      throws IOException {
    String id = (String) session.getAttribute("id");
    if (id == null) {
      id = "4"; // Anonymous user ID
      session.setAttribute("id", id);
    }

    SearchLog searchLog = searchLogMapper.getSearchLog(index);

    if (searchLog == null) {
      response.sendRedirect("/");
      return;
    }
    if (searchLog.getUserId() != null) {
      String userID = String.valueOf(searchLog.getUserId());
      if (!id.equalsIgnoreCase(userID)) {
        response.sendRedirect("/");
        return;
      } else {
        String filename = index + ".zip";
        //String logDir = "/home/PRIX/ACTG_log/";
        String logDir = "C:/ACTG_db/ACTG_db/log/";

        filename = new String(filename.getBytes("utf-8"));

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\""
            + filename + "\"");
        byte[] b = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(logDir + filename));
        OutputStream os = response.getOutputStream();

        int n;

        while ((n = bis.read(b, 0, b.length)) != -1) {
          os.write(b, 0, n);
        }
        os.flush();
        os.close();
        bis.close();
      }
    }
  }

  /**
   * dbnd result에서 download results 클릭시 호출
   * @param path
   * @return
   */
  @GetMapping("/modmap")
  public ResponseEntity<Resource> downloadFile(@RequestParam("path") Integer path, @RequestParam("name") String name) {
    String msFile = "";
    Integer rs = searchLogMapper.getMsFileByResult(path);
    if(rs != null){
      String rsForName = dataMapper.getNameById(rs);
      if(rsForName != null){
        msFile = rsForName;
      }
    }


    String target="";
    if( msFile.lastIndexOf('\\') != -1 ){
      target = msFile.substring(msFile.lastIndexOf('\\')+1, msFile.lastIndexOf('.'));
    }
    else if( msFile.lastIndexOf('/') != -1 ){
      target = msFile.substring(msFile.lastIndexOf('/')+1, msFile.lastIndexOf('.'));
    }
    else target = msFile.substring(0, msFile.lastIndexOf('.'));
  
    target += ".modmap.csv";
    msFile = name;
    // String fullPath = "E:/PRIX/data/" + msFile + ".modmap.csv"; 원래 이거인데 2024
    String fullPath = globalProperties.getPath() + "/data/" + msFile + ".modmap.csv";

    File file = new File(fullPath);
    if (!file.exists()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    Resource resource = new FileSystemResource(file);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + target);

    return ResponseEntity.ok()
        .headers(headers)
        .body(resource);
  }
}
