package com.prix.homepage.user.dto.modificationLogDto;

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
public class ModificationLogResponseDto {
 
  @NotNull
  private Integer id;

  private LocalDate date;

  private String version;

  private String file;
}
