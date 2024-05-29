package com.prix.homepage.livesearch.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prix.homepage.user.pojo.Enzyme;
import com.prix.homepage.user.service.EnzymeService;

import lombok.AllArgsConstructor;

/**
 * modplus에서 Enzyme에 해당하는 enzyme list를 갱신하기 위한 rest controller
 */
@RestController
@AllArgsConstructor
public class EnzymeRestController {
  private final EnzymeService enzymeService;
  
  /**
   * user_enzyme?userId=
   * enzyme list를 동적으로 가져오고자 할때 사용
   * @param userId session에 저장된 id
   * @return admin(id=0)가 지정한 enzyme list와 user가 지정한 enzyme list를 합친 리스트 리턴
   */
  @GetMapping("/user_enzyme")
  public List<Enzyme> getUserEnzyme(@RequestParam Integer userId){
    List<Enzyme> listEnzymeByAdmin = enzymeService.getAllEnzymeByUserId(0);
    List<Enzyme> listEnzymeByUser = enzymeService.getAllEnzymeByUserId(userId);

    List<Enzyme> combinedList = new ArrayList<>(listEnzymeByAdmin);
    combinedList.addAll(listEnzymeByUser);
        
    return combinedList;
  }
}
