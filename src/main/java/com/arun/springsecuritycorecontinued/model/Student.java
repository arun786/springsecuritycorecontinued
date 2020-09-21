package com.arun.springsecuritycorecontinued.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * @author arun on 9/19/20
 */

@Getter
@Setter
public class Student {
    @JsonIgnore
    private Integer id;
    private String name;
    private String standard;
    private Integer age;
}
