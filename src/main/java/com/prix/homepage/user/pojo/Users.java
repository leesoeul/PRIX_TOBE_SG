package com.prix.homepage.user.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    private Integer id;

    private String name;

    private String email;

    private String affiliation;

    private Integer level;
}
