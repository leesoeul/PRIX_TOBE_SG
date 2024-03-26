package com.prix.homepage.user.domain;

import java.time.LocalDate;

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
public class ModificationLog{
  
  private Integer id;

  private LocalDate date;

  private String version;

  private String file;
}
