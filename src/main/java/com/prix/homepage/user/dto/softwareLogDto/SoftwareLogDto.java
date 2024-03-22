package com.prix.homepage.user.dto.softwareLogDto;

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
public class SoftwareLogDto {
  
  @NotNull
  private String name;

  private LocalDate date;

  @NotNull
  private String version;

  @NotNull
  private String file;


}
