package com.prix.homepage.user.pojo;

import java.time.LocalDate;

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
public class SearchLogUser {

    private Integer id;

    private Integer userId;

    private String title;

    private LocalDate date;

    private Integer msfile;

    private Integer db;

    private String result;

    private String engine;
    
}
