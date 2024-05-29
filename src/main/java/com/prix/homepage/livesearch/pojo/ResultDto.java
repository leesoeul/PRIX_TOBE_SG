package com.prix.homepage.livesearch.pojo;

import com.prix.homepage.constants.ProteinSummary;
import com.prix.homepage.constants.JobProcess;
import com.prix.homepage.constants.Modification;
import com.prix.homepage.constants.ProteinInfo;

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
public class ResultDto {
  ProteinSummary summary;
  Modification[] mods;
  String fileName;
  boolean isDBond;
  boolean useTargetDecoy;
  String minScore;
  String maxHit;
  String minFDR;
  String sort;
  
  Integer userId;
  JobProcess jobState;
  double max;
  ProteinInfo[] proteins;
  int maxProteins;
  Integer level;
}
