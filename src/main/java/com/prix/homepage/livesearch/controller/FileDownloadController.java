package com.prix.homepage.livesearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prix.homepage.livesearch.dao.SearchLogMapper;
import com.prix.homepage.livesearch.pojo.SearchLog;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedInputStream;
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

    @GetMapping("/ACTG/download")
    public void downloadFile(@RequestParam("index") String index, HttpServletResponse response, HttpSession session) throws IOException {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            id = "4"; // Anonymous user ID
            session.setAttribute("id", id);
        }

        SearchLog searchLog = searchLogMapper.getSearchLog(index);

        if(searchLog == null){
          response.sendRedirect("/");
          return;
        }
        if(searchLog.getUserId() != null){
          String userID = String.valueOf(searchLog.getUserId());
          if(!id.equalsIgnoreCase(userID)){
            response.sendRedirect("/");
            return;
          }else{
            String filename = index +".zip";
            String logDir = "/home/PRIX/ACTG_log/";
        
            filename = new String(filename.getBytes("utf-8"));
        
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                + filename + "\"");
                byte[] b = new byte[1024];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(logDir + filename));
                OutputStream os = response.getOutputStream();
            
                int n;
            
                while ((n = bis.read(b, 0, b.length)) != -1) 
                {
                  os.write(b, 0, n);
                }
                os.flush(); 
                os.close();
                bis.close();
          }
        }
    }
}
