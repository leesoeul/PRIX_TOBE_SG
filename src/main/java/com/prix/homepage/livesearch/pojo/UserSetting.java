package com.prix.homepage.livesearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSetting {

  public enum PtolEnum {
    Da,
    ppm
  }
  
  private Integer userId;

  private Integer enzyme;

  private Byte mmc;

  private Byte met;

  private Float ptol;

  private PtolEnum ptolUnit;

  private Float ftol;

  private Float mmMin;

  private Float mmMax;

  private String engine;

  private String dataFormat;

  private String instrument;

  private String msResolution;

  private String msmsResolution;
}
