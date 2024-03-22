package com.prix.homepage.constants.entity;

import com.prix.homepage.constants.entity.id.UserEnzymeId;
import com.prix.homepage.livesearch.entity.Enzyme;
import com.prix.homepage.user.entity.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserEnzymeId.class)
@Entity
@Table(name = "px_user_enzyme")
public class UserEnzyme {

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private Account userId;

  @Id
  @ManyToOne
  @JoinColumn(name = "enzyme_id", referencedColumnName = "id")
  private Enzyme enzymeId;

}
