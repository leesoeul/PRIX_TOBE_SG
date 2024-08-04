package com.prix.homepage.livesearch.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatternMatchDto {

    private String db_type;

    private String pattern1;
    private String pattern2;
    private String pattern3;
    private String pattern4;
    private String pattern5;

    private String format_type;
    private String check_order;
    private String check_except;
    private String check_species;
    private String species;

}
