package com.prix.homepage.user.controller;

import com.prix.homepage.user.pojo.RequestLog;
import com.prix.homepage.user.pojo.SoftwareMsg;
import com.prix.homepage.user.service.RequestLogService;
import com.prix.homepage.user.service.SoftwareMsgService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.prix.homepage.download.Mailer;;


@Controller
@AllArgsConstructor
public class RequestLogController {
    private final RequestLogService requestLogService;
    private final SoftwareMsgService softwareMsgService;
    /**
     * prix.hanyang.ac.kr/admin/requestlog로의 get request 매핑
     */
    @GetMapping("/admin/requestlog")
    public String gotoRequestLogPage(Model model, HttpServletRequest request) {
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
        List<RequestLog> requestLogDto = requestLogService.getAllRequestLog();
        model.addAttribute("requestLogDto", requestLogDto);

        return "admin/requestlog";
    }
    //요청 수락 삭제시 get reqAccDel 매팅
    @PostMapping("/admin/reqAccDel")
    public String manage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        if (userId == null) {
            return "redirect:/admin/adlogin";
        }
        try{
            if(request.getParameter("btup") != null){
                //이메일 전송
                Mailer mt = new Mailer();
                String username = request.getParameter("username");
                String useremail = request.getParameter("useremail");
                String software = request.getParameter("software");
                //원본에서는 px_software_msg에서 가져오는데 예시가 없어서 구현 일시중지
                String message = softwareMsgService.getSoftwareMsg(software);
                String signature = softwareMsgService.getSoftwareMsg("signature");
                mt.sendEmailToUser(username, useremail, software, message, signature, null);
                requestLogService.updateState(Integer.parseInt(request.getParameter("request_id")), 1);
            }
            else if (request.getParameter("deluser") != null){
                //요청 삭제
                requestLogService.deleteRequest(Integer.parseInt(request.getParameter("request_id")));
            }
        } catch (Exception e) {
            //문제 생기면 에러 반환
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request.");
            return "redirect:/admin/requestlog";
        }

        return "redirect:/admin/requestlog";
    }
    
}
