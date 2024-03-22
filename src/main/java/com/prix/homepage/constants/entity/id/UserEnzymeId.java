package com.prix.homepage.constants.entity.id;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserEnzymeId implements Serializable {
  private Integer userId;
  private Integer enzymeId;
}