package com.prix.homepage.constants.domain;

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
  private Integer user_id;

  @NotNull
  private String name;

  private String fullname;

  private Integer columnClass;

  @NotNull
  private Double mass_diff;

  private Double avg_mass_diff;

  @NotNull
  private String residue;

  private String position;
  
}
