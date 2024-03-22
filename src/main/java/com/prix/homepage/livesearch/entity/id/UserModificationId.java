package com.prix.homepage.livesearch.entity.id;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserModificationId implements Serializable {

  private Integer userId;
  private Integer modId;

}