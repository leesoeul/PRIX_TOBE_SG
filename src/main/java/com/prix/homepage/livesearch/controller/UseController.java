package com.prix.homepage.livesearch.controller;


import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * USE 페이지
 */
@Controller
@AllArgsConstructor
public class UseController {
    @GetMapping("/livesearch/USE")
    public String USEPage() {
        return "livesearch/use_enclosure";
    }

    @GetMapping("/useContent")
    public String useContent() {
        return "livesearch/USE";
    }



    @GetMapping("helpOverlay")
    public String helpOverlay() {
        return "livesearch/use/helpOverlay";
    }
    @GetMapping("HotTableTemplate")
    public String HotTableTemplate() {
        return "livesearch/use/HotTableTemplate";
    }
    @GetMapping("HotTableTemplateBottom")
    public String HotTableTemplateBottom() {
        return "livesearch/use/HotTableTemplateBottom";
    }
    @GetMapping("ModalTemplate")
    public String ModalTemplate() {
        return "livesearch/use/ModalTemplate";
    }
    @GetMapping("ModalTemplateConfirmation")
    public String ModalTemplateConfirmation() {
        return "livesearch/use/ModalTemplateConfirmation";
    }
    @GetMapping("ModalTemplateLoading")
    public String ModalTemplateLoading() {
        return "livesearch/use/ModalTemplateLoading";
    }
    @GetMapping("ModalTemplateNew")
    public String ModalTemplateNew() {
        return "livesearch/use/ModalTemplateNew";
    }
}
