package com.prix.homepage.user.dto.databaseDto;

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
public class DatabaseResponseDto {
   
  @NotNull
  private Integer id;

  private String name;

  private String file;

  private Integer data_id;
}
