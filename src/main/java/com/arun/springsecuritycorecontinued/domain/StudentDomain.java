package com.arun.springsecuritycorecontinued.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author arun on 9/19/20
 */

@Entity(name = "student")
@Getter
@Setter
public class StudentDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String standard;
    private Integer age;
}
