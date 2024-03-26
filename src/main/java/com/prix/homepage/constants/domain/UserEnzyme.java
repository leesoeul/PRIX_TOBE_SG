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
public class UserEnzyme {

  @NotNull
  private Integer userId;

  @NotNull
  private Integer enzymeId;

}
