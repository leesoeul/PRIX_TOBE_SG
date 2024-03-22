package com.prix.homepage.download.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "px_software_request")
public class SoftwareRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private Timestamp date;

  @Column(nullable = false)
  private String name;

  @Email
  @Column(nullable = false)
  private String email;

  private String instrument;

  private String software;

  private String version;
  
  @Column(nullable = false)
  private Integer state;

  private Timestamp senttime;

}
