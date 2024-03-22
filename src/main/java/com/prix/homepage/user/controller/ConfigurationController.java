package com.prix.homepage.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.prix.homepage.user.dto.databaseDto.DatabaseResponseDto;
import com.prix.homepage.user.dto.enzymeDto.EnzymeResponseDto;
import com.prix.homepage.user.dto.modificationLogDto.ModificationLogResponseDto;
import com.prix.homepage.user.dto.softwareLogDto.SoftwareLogResponseDto;
import com.prix.homepage.user.dto.softwareMsgDto.SoftwareMsgResponseDto;
import com.prix.homepage.user.service.DatabaseService;
import com.prix.homepage.user.service.EnzymeService;
import com.prix.homepage.user.service.ModificationLogService;
import com.prix.homepage.user.service.SoftwareLogService;
import com.prix.homepage.user.service.SoftwareMsgService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ConfigurationController {

    private final DatabaseService databaseService;
    private final EnzymeService enzymeService;
    private final ModificationLogService modificationLogService;
    private final SoftwareLogService softwareLogService;
    private final SoftwareMsgService softwareMsgService;


    @GetMapping("/admin/configuration")
    public String gotoConfigurationPage(Model model, HttpServletRequest request) {
        // 세션에서 id, level 받고 admin 체크
        HttpSession session = request.getSession();
        Object idObject = session.getAttribute("id");
        Integer level = (Integer)session.getAttribute("level");
        if(idObject == null){
            return "redirect:/admin/adlogin";
        } else if(level == null || level <= 1){
            return "redirect:/admin/index";
        }
        
        // 1. px_database : id, name, file  # 비었으면 빈 List []
        List<DatabaseResponseDto> listDatabaseResponseDto = databaseService.getAllDatabase();
        model.addAttribute("listDatabaseResponseDto", listDatabaseResponseDto);
    
        // 2. px_enzyme : id, name, nt_cleave, ct_cleave where user_id = 0
        List<EnzymeResponseDto> listEnzymeResponseDto = enzymeService.getAllEnzymeByUserId(0);
        model.addAttribute("listEnzymeResponseDto", listEnzymeResponseDto);
    
        // 3. px_modification_log :  date, version, file 
        List<ModificationLogResponseDto> listModificationLogResponseDto = modificationLogService.getAllModificationLog();
        model.addAttribute("listModificationLogResponseDto", listModificationLogResponseDto);
    
        // 4. px_software_log :  name, date, version, file order by date, id
        List<SoftwareLogResponseDto> listSoftwareLogResponseDto = softwareLogService.getAllSoftwareLog();
        model.addAttribute("listSoftwareLogResponseDto", listSoftwareLogResponseDto);
    
        // 5. px_software_msg :  message where id = 'mode', 'dbond', 'nextsearch', 'signature'
        List<SoftwareMsgResponseDto> listSoftwareMsgMode= softwareMsgService.getAllSoftwareMsgById("mode");
        List<SoftwareMsgResponseDto> listSoftwareMsgDbond = softwareMsgService.getAllSoftwareMsgById("dbond");
        List<SoftwareMsgResponseDto> listSoftwareMsgNextsearch = softwareMsgService.getAllSoftwareMsgById("nextsearch");
        List<SoftwareMsgResponseDto> listSoftwareMsgSignature = softwareMsgService.getAllSoftwareMsgById("signature");
        model.addAttribute("listSoftwareMsgMode", listSoftwareMsgMode);
        model.addAttribute("listSoftwareMsgDbond", listSoftwareMsgDbond);
        model.addAttribute("listSoftwareMsgNextsearch", listSoftwareMsgNextsearch);
        model.addAttribute("listSoftwareMsgSignature", listSoftwareMsgSignature);
        
        return "admin/configuration";
    }
    
}

