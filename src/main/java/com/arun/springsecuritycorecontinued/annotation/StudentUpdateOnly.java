package com.arun.springsecuritycorecontinued.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author arun on 9/21/20
 */

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('student.update')")
public @interface StudentUpdateOnly {
}
