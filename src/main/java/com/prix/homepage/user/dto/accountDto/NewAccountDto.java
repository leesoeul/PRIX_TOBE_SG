package com.prix.homepage.user.dto.accountDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class NewAccountDto extends AccountDto{
  
  @NotNull
  private String confirmedPassword;

}
