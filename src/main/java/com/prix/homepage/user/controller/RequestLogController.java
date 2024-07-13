package com.prix.homepage.user.controller;

import com.prix.homepage.user.pojo.RequestLog;
import com.prix.homepage.user.service.RequestLogService;
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
public class RequestLogController {
    private final RequestLogService requestLogService;
    /**
     * prix.hanyang.ac.kr/admin/requestlog로의 get request 매핑
     */
    @GetMapping("/admin/requestlog")
    public String gotoRequestLogPage() {
        return "admin/requestlog";
    }
}
