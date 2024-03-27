package com.prix.homepage.user.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class NewAccount extends Account{
  
  @NotNull
  private String confirmedPassword;

}
