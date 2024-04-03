package com.prix.homepage.download.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareRequest {

  private Integer id;

  @NotNull
  private Timestamp date;

  @NotNull
  private String name;

  @Email
  @NotNull
  private String email;

  private String instrument;

  private String software;

  private String version;
  
  @NotNull
  private Integer state;

  private Timestamp senttime;

}
