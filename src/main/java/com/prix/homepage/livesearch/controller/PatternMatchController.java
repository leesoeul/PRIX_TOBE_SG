package com.prix.homepage.livesearch.controller;

import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.prix.homepage.livesearch.patternMatch.PatternMatch;
import com.prix.homepage.livesearch.pojo.PatternMatchDto;
import com.prix.homepage.livesearch.service.PatternMatchService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class PatternMatchController {

    private final PatternMatchService patternMatchService;

    @GetMapping("/patternMatch/patternMatchFrm")
    public String patternMatchPage(Model model) {

        // genbank update date
        String gd = "";
        // swiss_prot update date
        String sd = "";

        gd = patternMatchService.getUpdateDay("genbank");
        sd = patternMatchService.getUpdateDay("swiss_prot");

        PatternMatchDto patternMatchDto = PatternMatchDto.builder()
                .db_type("0")
                .pattern1(null)
                .pattern2(null)
                .pattern3(null)
                .pattern4(null)
                .pattern5(null)
                .format_type("0")
                .check_order(null)
                .check_except(null)
                .check_species(null)
                .species(null)
                .build();

        model.addAttribute("gd", gd);
        model.addAttribute("sd", sd);
        model.addAttribute("patternMatchDto", patternMatchDto);

        return "livesearch/patternMatchFrm";
    }

    @PostMapping("/patternMatch/patternMatchResult")
    public String getPatternMatchResult(Model model, PatternMatchDto patternMatchDto) {

        try {
            String db_t = patternMatchDto.getDb_type().equals("0") ? "swiss_prot" : "genbank";
            String sd = "";
            sd = patternMatchService.getUpdateDay(db_t);
            // out.print("<li>" + db_t + " Release : (" + sd + ")</li><br>");
            // out.flush(); // 클라이언트에게 즉시 전송

            String[] pattern = { patternMatchDto.getPattern1(),
                    patternMatchDto.getPattern2(),
                    patternMatchDto.getPattern3(), patternMatchDto.getPattern4(),
                    patternMatchDto.getPattern5() };
            pattern = java.util.Arrays.stream(pattern).filter(p -> p != null &&
                    !p.equals("")).toArray(String[]::new);

            String format_type = patternMatchDto.getFormat_type().equals("0") ? "PROSITE"
                    : "PYTHON";
            boolean check_order = patternMatchDto.getCheck_order() != null;
            boolean check_except = patternMatchDto.getCheck_except() != null;
            boolean check_species = patternMatchDto.getCheck_species() != null;
            String species = patternMatchDto.getSpecies();

            PatternMatch p = new PatternMatch(format_type, db_t, pattern, check_species,
                    species, check_except, check_order);
            p.DBParameter("prix", "Prix2024!@", "166.104.110.37/");
            // System.out.println("Form type: " + format_type + ", db_type: " + db_t);

            StringBuilder result = new StringBuilder();
            p.MainMethod();
            // String oneResult = p.getOneProtein();
            String oneResult = "";
            // p.MainMethod();
            while (oneResult != null) {
                result.append(oneResult);
                // out.print(oneResult + "<br>"); // 결과를 화면에 출력
                oneResult = p.getOneProtein();
                // out.flush(); // 클라이언트에게 바로 전송
            }
            result.append(p.getParameter());
            // out.println(p.getParameter() + "<br>");

            model.addAttribute("dbType", db_t);
            model.addAttribute("date", sd);
            model.addAttribute("result", result.toString());

        } catch (Exception e) {
            // try (PrintWriter out = response.getWriter()) {
            // out.println("Error");
            // out.println("" + e.getStackTrace()[0]);
            // e.printStackTrace(out);
            // } catch (Exception ex) {
            e.printStackTrace();
            // }
        }

        return "livesearch/patternMatchResult";
    }

}
