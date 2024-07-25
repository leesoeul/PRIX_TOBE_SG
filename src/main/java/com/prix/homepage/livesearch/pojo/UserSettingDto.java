package com.prix.homepage.livesearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * UserSettingService -> Controller로 전달 용도, engine = dbond
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserSettingDto {
  private String version;
  private String enzyme;
  private String missedCleavage;
  private String pTolerance;
  private String pUnit;
  private String fTolerance;
  private String dataFormat;
  private String instrument;
}
