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
public class JobQueue {
  
  private Integer id;

  @NotNull
  private Integer user_id;

  @NotNull
  private String job_code;

  private String title;

}
