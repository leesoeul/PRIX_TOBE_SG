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
public class RequestLog {
    
    private Integer id;

    private LocalDate date;

    private String name;

    private String affiliation;

    private String title;

    private String email;

    private String instrument;

    private String software;

    private String version;

    private Integer state;


}
