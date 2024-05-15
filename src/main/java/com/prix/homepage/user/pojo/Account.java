package com.prix.homepage.user.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Account {

  private Integer id;

  private String userid;
  
  @NotNull
  private String password;

  private String name;
  public String getPassword(){
    return password;
  }

  // @Email
  private String email;

  private String affiliation;

  @Min(value = 1, message = "Level must be 1 or 2")
  @Max(value = 2, message = "Level must be 1 or 2")
  private Integer level;
}
