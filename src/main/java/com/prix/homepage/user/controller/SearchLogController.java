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

import java.util.List;

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

        //int offset = page * size;
        List<SearchLog> listSearchLogDto = searchLogService.getAllSearchLog(userId);
        //List<SearchLog> listSearchLogDto = searchLogService.getSearchLogs(userId, offset, size);
        //int totalLogs = searchLogService.getTotalLogs(userId);
        //int totalPages = (int) Math.ceil((double) totalLogs / size);

        model.addAttribute("listSearchLogDto", listSearchLogDto);
        //model.addAttribute("currentPage", page);
        //model.addAttribute("totalPages", totalPages);

        return "admin/searchlog";
    }
}
