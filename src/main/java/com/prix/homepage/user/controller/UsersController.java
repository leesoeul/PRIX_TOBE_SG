package com.prix.homepage.user.controller;

import com.prix.homepage.user.pojo.Users;
import com.prix.homepage.user.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        List<Users> usersDto = usersService.getAllUsers();
        model.addAttribute("usersDto", usersDto);


        return "admin/users";
    }
    //이용자 삭제 요청 받을시 (users에서 del 버튼)
    @PostMapping("/admin/deluser")
    public String manage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        if (userId == null) {
            return "redirect:/admin/adlogin";
        }
        try{
        usersService.deleteAccount(Integer.parseInt(request.getParameter("user_id")));
        } catch (Exception e) {
            //문제 생기면 에러 반환
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request.");
            return "redirect:/admin/users";
        }

        return "redirect:/admin/users";
    }
    
}
