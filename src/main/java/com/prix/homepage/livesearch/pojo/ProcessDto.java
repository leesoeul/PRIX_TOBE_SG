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
public class ProcessDto {
  Boolean finished;
  Boolean failed;

  String logPath;
  String xmlPath;
  String msPath;
  String dbPath;
  String decoyPath;
  String multiPath;

  String title;
  Integer msIndex;
  Integer dbIndex;
  String engine;
  String jobCode;
  Integer refreshCount;

  String fileMsg;
  String output;

  Integer rate;
  String returnAddr;
}
