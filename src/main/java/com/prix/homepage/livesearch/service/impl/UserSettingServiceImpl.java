package com.prix.homepage.livesearch.service.impl;

import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.dao.UserSettingMapper;
import com.prix.homepage.livesearch.pojo.UserSetting;
import com.prix.homepage.livesearch.pojo.UserSettingDto;
import com.prix.homepage.livesearch.service.UserSettingService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserSettingServiceImpl implements UserSettingService {

  private final UserSettingMapper userSettingMapper;

  @Override
  public UserSettingDto getUsersettingById(Integer id) {
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
    UserSettingDto dummyUserSettingDto = UserSettingDto.builder()
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

    UserSetting userSetting = userSettingMapper.findByUserIdModeye(id);
    if (userSetting != null) {
      enzyme = "" + userSetting.getEnzyme();
      missedCleavage = "" + userSetting.getMmc();
      minNumEnzTerm = "" + userSetting.getMet();
      pTolerance = "" + userSetting.getPtol();
      pUnit = "" + userSetting.getPtol_unit();
      fTolerance = "" + userSetting.getFtol();
      minMM = "" + userSetting.getMm_min();
      maxMM = "" + userSetting.getMm_max();
      dataFormat = userSetting.getData_format();
      instrument = userSetting.getInstrument();
      msResolution = userSetting.getMs_resolution();
      if (msResolution == null)
        msResolution = "";
      msmsResolution = userSetting.getMsms_resolution();
      if (msmsResolution == null)
        msmsResolution = "";

      UserSettingDto userSettingDto = UserSettingDto.builder()
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
  }
}
