package com.prix.homepage.user.controller;

import com.prix.homepage.user.service.EnzymeService;
import com.prix.homepage.user.service.DatabaseService;
import com.prix.homepage.user.service.SoftwareMsgService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class ManageController {

    private final EnzymeService enzymeService;
    private final DatabaseService databaseService;
    private final SoftwareMsgService softwareMsgService;

    //Manage요청 받을시 (configuration에서 edit, unlink 버튼)
    @PostMapping("/admin/manage")
    public String manage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        if (userId == null) {
            return "redirect:/admin/adlogin";
        }

        try {
            if (request.getParameter("delete_enzyme") != null) {
                enzymeService.deleteEnzyme(Integer.parseInt(request.getParameter("enzyme_id")), userId);
            } else if (request.getParameter("add_enzyme") != null) {
                String name = request.getParameter("nenzyme_name");
                String cut = request.getParameter("nenzyme_nt_cut");
                String term = request.getParameter("nenzyme_ct_cut");
                enzymeService.insertEnzyme(userId, name, cut, term);
            } else if(request.getParameter("modify_enzyme") != null){
                Integer enzymeId = Integer.parseInt(request.getParameter("enzyme_id"));
                String name = request.getParameter("enzyme_name");
                String cut = request.getParameter("enzyme_nt_cut");
                String term = request.getParameter("enzyme_ct_cut");
                enzymeService.updateEnzyme(enzymeId, userId, name, cut, term);
                System.out.println("aaasdadas");
            } else if (request.getParameter("delete_db") != null) {
                databaseService.deleteDatabase(Integer.parseInt(request.getParameter("db_index")));
            } else if (request.getParameter("db_name") != null) {
                String name = request.getParameter("db_name");
                String dbId = request.getParameter("db_index");

                if (dbId != null) {
                    Integer id = Integer.parseInt(dbId);
                    databaseService.updateDatabase(id, name);
                }
            //메시지 바꾸기 영역 (update 기능)
            } else if (request.getParameter("modify_swmsg") != null) {
                softwareMsgService.updateSoftwareMsg("mode", request.getParameter("modeMessage"));
                softwareMsgService.updateSoftwareMsg("dbond", request.getParameter("dbondMessage"));
                softwareMsgService.updateSoftwareMsg("nextsearch", request.getParameter("nxtsrchMessage"));
                softwareMsgService.updateSoftwareMsg("signature", request.getParameter("signatureMessage"));
                
            } 
        } catch (Exception e) {
            //문제 생기면 에러 반환
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request.");
            return "redirect:/admin/configuration";
        }
        
        return "redirect:/admin/configuration";
    }


    //Manage요청 받을시 (configuration에서 edit, unlink 버튼)
    @PostMapping("/admin/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        if (userId == null) {
            return "redirect:/admin/adlogin";
        }
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/admin/configuration";
        }

        try {
            if (request.getParameter("add_db") != null){
                //DB에 파일 추가
            } else if (request.getParameter("ptm_add") != null){
                //Modifications 부분 파일 추가
            } else if (request.getParameter("sftw_add") != null){
                //소프트웨어 부분 파일 추가
            }
            
        } catch (Exception e) {
            //문제 생기면 에러 반환
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request.");
            return "redirect:/admin/configuration";
        }
        
        return "redirect:/admin/configuration";
    }
}
