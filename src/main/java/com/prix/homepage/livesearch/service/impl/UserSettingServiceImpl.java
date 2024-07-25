package com.prix.homepage.livesearch.service.impl;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.dao.UserSettingMapper;
import com.prix.homepage.livesearch.pojo.UserSetting;
import com.prix.homepage.livesearch.pojo.UserSettingDto;
import com.prix.homepage.livesearch.pojo.UserSettingModeyeDto;
import com.prix.homepage.livesearch.service.UserSettingService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserSettingServiceImpl implements UserSettingService {

  private final UserSettingMapper userSettingMapper;

  @Override
  public UserSettingDto getUsersetting(Integer id, String engine) {
    if (engine.equals("modeye")) {
      String version = "1.01";
      String enzyme = "";
      String missedCleavage = "1";
      String minNumEnzTerm = "2";
      String pTolerance = "10";
      String minChar = "2";
      String pUnit = "ppm";
      String fTolerance = "0.05";
      String minIE = "0";
      String maxIE = "1";
      String minMM = "-150";
      String maxMM = "+250";
      String dataFormat = "";
      String instrument = "";
      String msResolution = "";
      String msmsResolution = "";

      // id에 해당하는 usersetting이 존재하지 않을시 보낼 dummy
      UserSettingModeyeDto dummyUserSettingDto = UserSettingModeyeDto.builder()
          .version(version)
          .enzyme(enzyme)
          .missedCleavage(missedCleavage)
          .minNumEnzTerm(minNumEnzTerm)
          .pTolerance(pTolerance)
          .minChar(minChar)
          .pUnit(pUnit)
          .fTolerance(fTolerance)
          .minIE(minIE)
          .maxIE(maxIE)
          .minMM(minMM)
          .maxMM(maxMM)
          .dataFormat(dataFormat)
          .instrument(instrument)
          .msResolution(msResolution)
          .msmsResolution(msmsResolution)
          .build();

      UserSetting userSetting = userSettingMapper.find(id, "modeye");
      if (userSetting != null) {
        enzyme = "" + userSetting.getEnzyme();
        missedCleavage = "" + userSetting.getMmc();
        minNumEnzTerm = "" + userSetting.getMet();
        pTolerance = "" + userSetting.getPtol();
        pUnit = "" + userSetting.getPtolUnit();
        fTolerance = "" + userSetting.getFtol();
        minMM = "" + userSetting.getMmMin();
        maxMM = "" + userSetting.getMmMax();
        dataFormat = userSetting.getDataFormat();
        instrument = userSetting.getInstrument();
        msResolution = userSetting.getMsResolution();
        if (msResolution == null)
          msResolution = "";
        msmsResolution = userSetting.getMsmsResolution();
        if (msmsResolution == null)
          msmsResolution = "";

        UserSettingModeyeDto userSettingDto = UserSettingModeyeDto.builder()
            .version(version)
            .enzyme(enzyme)
            .missedCleavage(missedCleavage)
            .minNumEnzTerm(minNumEnzTerm)
            .pTolerance(pTolerance)
            .minChar(minChar)
            .pUnit(pUnit)
            .fTolerance(fTolerance)
            .minIE(minIE)
            .maxIE(maxIE)
            .minMM(minMM)
            .maxMM(maxMM)
            .dataFormat(dataFormat)
            .instrument(instrument)
            .msResolution(msmsResolution)
            .msmsResolution(msmsResolution)
            .build();

        return userSettingDto;
      }
      return dummyUserSettingDto;
    } else if (engine.equals("dbond")) {
      String version = "3.01";
      String enzyme = "";
      String missedCleavage = "1";
      String pTolerance = "0.5";
      String pUnit = "Da";
      String fTolerance = "0.5";
      String dataFormat = "";
      String instrument = "";

      UserSettingDto dummyUserSettingDto = UserSettingDto.builder().version(version).enzyme(enzyme)
          .missedCleavage(missedCleavage).pTolerance(pTolerance).pUnit(pUnit).fTolerance(fTolerance)
          .dataFormat(dataFormat).instrument(instrument).build();

      UserSetting userSetting = userSettingMapper.find(id, engine);
      if (userSetting != null) {
        enzyme = "" + userSetting.getEnzyme();
        missedCleavage = "" + userSetting.getMmc();
        pTolerance = "" + userSetting.getPtol();
        pUnit = "" + userSetting.getPtolUnit();
        fTolerance = "" + userSetting.getFtol();
        dataFormat = userSetting.getDataFormat();
        instrument = userSetting.getInstrument();

        UserSettingDto userSettingDto = UserSettingDto.builder().version(version).enzyme(enzyme)
            .missedCleavage(missedCleavage).pTolerance(pTolerance).pUnit(pUnit).fTolerance(fTolerance)
            .dataFormat(dataFormat).instrument(instrument).build();

        return userSettingDto;
      }
      return dummyUserSettingDto;
    }
    return null;
  }
}
