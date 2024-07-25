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
public class SearchLog {

    private Integer id;

    private Integer user_id;

    private String title;

    private LocalDate date;

    private Integer msfile;

    private Integer db;

    private Integer result;

    private String engine;
    
}
