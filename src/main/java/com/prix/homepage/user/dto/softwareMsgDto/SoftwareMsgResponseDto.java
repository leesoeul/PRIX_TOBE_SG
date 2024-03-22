package com.prix.homepage.user.dto.softwareMsgDto;

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
public class SoftwareMsgResponseDto {
  @NotNull
  private String id;

  @NotNull
  private String message;
}
