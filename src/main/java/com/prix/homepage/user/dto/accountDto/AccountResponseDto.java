package com.prix.homepage.user.dto.accountDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDto {
    
  @NotNull
  private Integer id;

  private String userid;

  @NotNull
  private String password;

  private String name;

  // @Email
  private String email;

  private String affiliation;

  @Min(value = 1, message = "Level must be 1 or 2")
  @Max(value = 2, message = "Level must be 1 or 2")
  private Integer level;
}
