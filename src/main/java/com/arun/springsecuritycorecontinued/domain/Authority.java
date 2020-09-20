package com.arun.springsecuritycorecontinued.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author arun on 9/18/20
 */

@Entity
@Getter
@Setter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;
}
