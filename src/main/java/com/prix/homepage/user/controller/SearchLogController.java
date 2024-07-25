package com.prix.homepage.user.controller;

import com.prix.homepage.user.pojo.SearchLog;
import com.prix.homepage.user.service.SearchLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@AllArgsConstructor
public class SearchLogController {
    private final SearchLogService searchLogService;

    @GetMapping("/admin/searchlog")
    public String gotoSearchLogPage(Model model, HttpServletRequest request,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        HttpSession session = request.getSession();
        Object idObject = session.getAttribute("id");
        Integer level = (Integer) session.getAttribute("level");
        Integer userId = (Integer) session.getAttribute("id");

        if (idObject == null) {
            return "redirect:/admin/adlogin";
        } else if (level == null || level <= 1) {
            return "redirect:/admin/index";
        }

        List<SearchLog> searchLogDto = searchLogService.getAllSearchLog();
        
        //Map 형태로 name, msfile, db의 이름 가져오기
        Map<Integer, String> userNames = new HashMap<>();
        for(SearchLog searchLog : searchLogDto){
            Integer id = searchLog.getUser_id();
            System.out.println(id);
            String userName = searchLogService.findName(id);
            System.out.println(userName);
            userNames.put(id, userName);
        }
        Map<Integer, String> msFiles = new HashMap<>();
        for(SearchLog searchLog : searchLogDto){
            Integer id = searchLog.getMsfile();
            System.out.println(id);
            String fileName = searchLogService.findFile(id);
            System.out.println(fileName);
            msFiles.put(id, fileName);
        }
        Map<Integer, String> dbNames = new HashMap<>();
        for(SearchLog searchLog : searchLogDto){
            Integer id = searchLog.getDb();
            System.out.println(id);
            String fileName = searchLogService.findFile(id);
            System.out.println(fileName);
            dbNames.put(id, fileName);
        }


        model.addAttribute("searchLogDto", searchLogDto);
        model.addAttribute("userNames", userNames);
        model.addAttribute("msFiles", msFiles);
        model.addAttribute("dbNames", dbNames);
        

        return "admin/searchlog";
    }
}
