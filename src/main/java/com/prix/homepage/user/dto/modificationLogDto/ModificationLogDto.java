package com.prix.homepage.user.dto.modificationLogDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModificationLogDto {
  
  private LocalDate date;

  private String version;

  private String file;
}
