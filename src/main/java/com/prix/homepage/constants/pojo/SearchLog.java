package com.prix.homepage.constants.pojo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchLog {
  
  private Integer id;

  @NotNull
  private Integer user_id;

  private String title;

  private LocalDate date;

  private Integer msfile;

  private Integer db;

  private Integer result;

  private String engine;

}
