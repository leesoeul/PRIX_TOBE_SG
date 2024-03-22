package com.prix.homepage.user.dto.softwareLogDto;

import java.time.LocalDate;

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
public class SoftwareLogResponseDto {
  
  @NotNull
  private Integer id;

  @NotNull
  private String name;

  private LocalDate date;

  @NotNull
  private String version;

  @NotNull
  private String file;

}
