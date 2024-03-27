package com.prix.homepage.constants.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    
  private Integer id;

  @NotNull
  private String type;

  private String name;

  private byte[] content;

}
