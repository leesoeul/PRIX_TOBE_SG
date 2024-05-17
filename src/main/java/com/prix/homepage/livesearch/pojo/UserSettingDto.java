package com.prix.homepage.livesearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserSettingService -> Controller로 전달 용도
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettingDto {

  private String version;
  private String enzyme;
  private String missedCleavage;
  private String minNumEnzTerm;
  private String pTolerance;
  private String minChar;
  private String pUnit;
  private String fTolerance;
  private String minIE;
  private String maxIE;
  private String minMM;
  private String maxMM;
  private String dataFormat;
  private String instrument;
  private String msResolution;
  private String msmsResolution;
}
