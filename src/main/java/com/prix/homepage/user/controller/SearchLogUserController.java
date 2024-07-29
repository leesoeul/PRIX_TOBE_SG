package com.prix.homepage.user.controller;

import com.prix.homepage.user.pojo.SearchLogUser;
import com.prix.homepage.user.service.SearchLogUserService;
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
public class SearchLogUserController {
    private final SearchLogUserService searchLogService;

    @GetMapping("/admin/searchlog")
    public String gotoSearchLogPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object idObject = session.getAttribute("id");
        Integer level = (Integer) session.getAttribute("level");
        Integer userId = (Integer) session.getAttribute("id");

        if (idObject == null) {
            return "redirect:/admin/adlogin";
        } else if (level == null || level <= 1) {
            return "redirect:/admin/index";
        }

        List<SearchLogUser> searchLogDto = searchLogService.getAllSearchLog();
        
        //Map 형태로 name, msfile, db의 이름 가져오기
        Map<Integer, String> userNames = new HashMap<>();
        for(SearchLogUser searchLog : searchLogDto){
            Integer id = searchLog.getUserId();
            String userName = searchLogService.findName(id);
            userNames.put(id, userName);
        }
        Map<Integer, String> msFiles = new HashMap<>();
        for(SearchLogUser searchLog : searchLogDto){
            Integer id = searchLog.getMsfile();
            String fileName = searchLogService.findFile(id);
            msFiles.put(id, fileName);
        }
        Map<Integer, String> dbNames = new HashMap<>();
        for(SearchLogUser searchLog : searchLogDto){
            Integer id = searchLog.getDb();
            String fileName = searchLogService.findFile(id);
            dbNames.put(id, fileName);
        }


        model.addAttribute("searchLogDto", searchLogDto);
        model.addAttribute("userNames", userNames);
        model.addAttribute("msFiles", msFiles);
        model.addAttribute("dbNames", dbNames);
        

        return "admin/searchlog";
    }
}
