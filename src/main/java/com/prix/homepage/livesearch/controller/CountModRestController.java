package com.prix.homepage.livesearch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prix.homepage.livesearch.service.UserModificationService;

import lombok.AllArgsConstructor;

/**
 * var_ptms.html에서 동적으로 count만 추가하고자 할때 필요한 rest controller
 */
@RestController
@AllArgsConstructor
public class CountModRestController {
    private final UserModificationService userModificationService;

    /**
     * px_user_modification에서 userid와 engine이 일치하는 데이터를 리턴한다
     * @param userId 입력받은 user의 id
     * @param engine 입력받은 engine. db에선 bit(1) 
     * @return 일치하는 카운트 숫자
     */
    @GetMapping("/countModifications")
    public Integer countModifications(@RequestParam Integer userId, @RequestParam Integer variable,  @RequestParam Integer engine) {
      boolean engineBool = engine == 1;
      boolean variableBool = variable == 1;
      return userModificationService.countModifications(userId, variableBool, engineBool);
    }
}
