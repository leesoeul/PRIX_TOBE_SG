package com.prix.homepage.livesearch.pojo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ResultService 에서 controller로 전달하는 용도
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ACTGResultDto {
  LocalDate date;
  String userName;
  String title;
  String method;
  String IL;
  String proteinDB;
  String proteinOption;
  String VSGOption;
  String variantSpliceGraphDB;
  String index;
}
