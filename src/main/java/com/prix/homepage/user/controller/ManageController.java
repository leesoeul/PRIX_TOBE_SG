package com.prix.homepage.user.controller;

import com.prix.homepage.user.service.EnzymeService;
import com.prix.homepage.user.service.DatabaseService;
import com.prix.homepage.user.service.SoftwareMsgService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            String deleteEnzymeId = request.getParameter("delete_enzyme");
            if (deleteEnzymeId != null) {
                enzymeService.deleteEnzyme(Integer.parseInt(request.getParameter("enzyme_id")), userId);
            } else if (request.getParameter("enzyme_name") != null) {
                String name = request.getParameter("enzyme_name");
                String cut = request.getParameter("enzyme_nt_cut");
                String term = request.getParameter("enzyme_ct_cut");
                String enzymeId = request.getParameter("enzyme_id");

                if (enzymeId == null) {
                    enzymeService.insertEnzyme(userId, name, cut, term);
                } else {
                    Integer id = Integer.parseInt(enzymeId);
                    enzymeService.updateEnzyme(id, userId, name, cut, term);
                }
            } else if (request.getParameter("delete_db") != null) {
                databaseService.deleteDatabase(Integer.parseInt(request.getParameter("db_index")));
            } else if (request.getParameter("db_name") != null) {
                String name = request.getParameter("db_name");
                String dbId = request.getParameter("db_index");

                if (dbId != null) {
                    Integer id = Integer.parseInt(dbId);
                    databaseService.updateDatabase(id, name);
                }
            } else if (request.getParameter("modify_swmsg") != null) {
                softwareMsgService.updateSoftwareMsg("moda", request.getParameter("modamsg").replace("'", "\\'"));
                softwareMsgService.updateSoftwareMsg("dbond", request.getParameter("dbondmsg").replace("'", "\\'"));
                softwareMsgService.updateSoftwareMsg("signature", request.getParameter("signature").replace("'", "\\'"));
            }
        } catch (Exception e) {
            //문제 생기면 에러 반환
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request.");
            return "redirect:/admin/configuration";
        }

        return "redirect:/admin/configuration";
    }
}
