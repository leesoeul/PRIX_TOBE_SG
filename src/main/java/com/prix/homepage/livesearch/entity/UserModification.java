package com.prix.homepage.livesearch.entity;

import com.prix.homepage.constants.entity.Modification;
import com.prix.homepage.livesearch.entity.id.UserModificationId;
import com.prix.homepage.user.entity.Account;

import jakarta.persistence.Column;
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
@IdClass(UserModificationId.class)
@Entity
@Table(name = "px_user_modification")
public class UserModification {

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private Account userId;

  @Id
  @ManyToOne
  @JoinColumn(name = "mod_id", referencedColumnName = "id")
  private Modification modId;

  @Column(nullable = false)
  private Boolean variable;

  private Boolean engine;

}
