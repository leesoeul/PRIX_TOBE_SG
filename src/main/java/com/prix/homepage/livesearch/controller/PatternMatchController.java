package com.prix.homepage.livesearch.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.prix.homepage.livesearch.patternMatch.PatternMatch;
import com.prix.homepage.livesearch.pojo.PatternMatchDto;
import com.prix.homepage.livesearch.service.PatternMatchService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class PatternMatchController {

    private final PatternMatchService patternMatchService;

    @GetMapping("/patternMatch/patternMatchFrm")
    public String patternMatchPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        
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
            p.MainMethod();

            List<String> allResults = new ArrayList<String>();
            String oneResult = "";
            while (oneResult != null) {
                allResults.add(oneResult);
                oneResult = p.getOneProtein(); // 검색된 결과를 하나씩 가져온다
            }

            allResults.add(p.getParameter()); // 마지막에 찾은 protein과 pattern의 수를 화면에 출력

            model.addAttribute("dbType", db_t);
            model.addAttribute("date", sd);
            model.addAttribute("allResults", allResults); // 조건에 맞는 데이터set을 넘겨줌

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 08.18 메모
        // db에서 데이터를 실시간으로 받아오는 것이 아니었다.. 데이터가 클수록 가져오는데 시간이 오래걸림.
        // 실시간으로 받아오는 것처럼 보이는 건 랜더링 로딩이었다!
        // controller에서 out.print가 아닌 result.html 페이지에서 데이터를 랜더링하면 데이터가 큰 경우 터지는 버그ㅜ
        // 페이지 네이션은 실패
        // html 파일을 랜더링 하지 않고 파비콘과 탭 title을 설정하는 방법 구상해보기 끼야아아악
        // 08.19 메모
        // 한 번 가져온 result를 활용하자! db에서 받아온 데이터를 모두 클라이언트에게 넘긴다.
        // -> 클라이언트에서 페이지네이션 진행. 넘겨받은 result 중 일부만 랜더링하는 방식.

        return "livesearch/patternMatchResult";
    }

}
