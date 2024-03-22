package com.prix.homepage.livesearch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "px_user_setting")
public class UserSetting {

  public enum PtolEnum {
    Da,
    ppm
  }
  
  @Id
  private Integer user_id;

  private Integer enzyme;

  private Byte mmc;

  private Byte met;

  private Float ptol;

  @Enumerated(EnumType.STRING)
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
