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
public class UpdateTable {
  
  @NotNull
  private String date;
  
  @NotNull
  private String dbname;

}
