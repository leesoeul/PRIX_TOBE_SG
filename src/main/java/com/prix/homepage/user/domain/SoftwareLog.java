package com.prix.homepage.user.domain;

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
public class SoftwareLog {

  private Integer id;

  @NotNull
  private String name;

  private LocalDate date;

  @NotNull
  private String version;

  @NotNull
  private String file;


}
