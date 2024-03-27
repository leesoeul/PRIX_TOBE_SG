package com.prix.homepage.livesearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

  public enum PtolEnum {
    Da,
    ppm
  }
  
  private Integer user_id;

  private Integer enzyme;

  private Byte mmc;

  private Byte met;

  private Float ptol;

  private PtolEnum ptol_unit;

  private Float ftol;

  private Float mm_min;

  private Float mm_max;

  private String engine;

  private String data_format;

  private String instrument;

  private String ms_resolution;

  private String msms_resolution;
}
