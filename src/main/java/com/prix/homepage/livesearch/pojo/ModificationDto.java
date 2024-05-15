package com.prix.homepage.livesearch.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * px_classfication에서 class(String) 값과 그 외 Modification 값을 포함한 dto 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModificationDto {
  
  private Integer id;

  @NotNull
  private Integer userId;

  @NotNull
  private String name;

  private String fullname;

  private String columnClass;

  @NotNull
  private Double massDiff;

  private Double avgMassDiff;

  @NotNull
  private String residue;

  private String position;
  
}
