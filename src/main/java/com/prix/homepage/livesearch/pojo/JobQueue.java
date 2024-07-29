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
public class JobQueue {
  
  private Integer id;

  @NotNull
  private Integer userId;

  @NotNull
  private String jobCode;

  private String title;

}
