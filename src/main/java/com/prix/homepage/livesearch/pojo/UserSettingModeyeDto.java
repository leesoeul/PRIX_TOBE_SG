package com.prix.homepage.livesearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * UserSettingService -> Controller로 전달 용도, engine = modeye(modplus페이지)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserSettingModeyeDto extends UserSettingDto{

  private String minNumEnzTerm;
  private String minChar;
  private String minIE;
  private String maxIE;
  private String minMM;
  private String maxMM;
  private String msResolution;
  private String msmsResolution;
}
