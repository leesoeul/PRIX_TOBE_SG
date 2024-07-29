package com.prix.homepage.livesearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ProcessService에서 Controller로 전달되는 용도
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ACTGProcessDto {
  boolean finished;
  boolean failed;

  String processName;
  String title;
  String output;
  String prixIndex;

  Integer rate;

}
