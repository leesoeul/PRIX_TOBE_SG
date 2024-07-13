package com.prix.homepage.user.controller;

import com.prix.homepage.user.pojo.Users;
import com.prix.homepage.user.service.UsersService;
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
public class UsersController {
    private final UsersService usersService;
    /**
     * prix.hanyang.ac.kr/admin/users로의 get request 매핑
     */
    @GetMapping("/admin/users")
    public String gotoAdminUsersPage(Model model, HttpServletRequest request) {
        // 세션에서 id, level 받고 admin 체크
        HttpSession session = request.getSession();
        Object idObject = session.getAttribute("id");
        Integer level = (Integer)session.getAttribute("level");
        Integer userId = (Integer) session.getAttribute("id");
        if(idObject == null){
            return "redirect:/admin/adlogin";
        } else if(level == null || level <= 1){
            return "redirect:/admin/index";
        }
        return "admin/users";
    }
    
}
