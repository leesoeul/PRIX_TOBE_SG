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
public class UserModification {

  @NotNull
  private Integer userId;

  @NotNull
  private Integer modId;

  @NotNull
  private Boolean variable;

  private Boolean engine;

}
