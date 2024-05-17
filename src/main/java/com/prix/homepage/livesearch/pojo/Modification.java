package com.prix.homepage.livesearch.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Modification {
  
  private Integer id;

  @NotNull
  private Integer userId;

  @NotNull
  private String name;

  private String fullname;

  private Integer columnClass;

  @NotNull
  private Double massDiff;

  private Double avgMassDiff;

  @NotNull
  private String residue;

  private String position;
  
}
