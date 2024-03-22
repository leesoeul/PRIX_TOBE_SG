package com.prix.homepage.constants.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "px_modification")
public class Modification {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private Integer user_id;

  @Column(nullable = false)
  private String name;

  private String fullname;

  @Column(name = "class")
  private Integer columnClass;

  @Column(nullable = false)
  private Double mass_diff;

  private Double avg_mass_diff;

  @Column(nullable = false)
  private String residue;

  private String position;
  
}
