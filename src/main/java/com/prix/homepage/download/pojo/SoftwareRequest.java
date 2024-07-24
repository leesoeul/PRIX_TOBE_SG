package com.prix.homepage.download.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoftwareRequest {

  @NotNull
  private LocalDateTime date;

  @NotNull
  private String name;

  @NotNull
  private String affiliation;

  @NotNull
  private String title;

  @Email
  @NotNull
  private String email;

  private String instrument;

  private String software;

  @NotNull
  private Integer state;

}
