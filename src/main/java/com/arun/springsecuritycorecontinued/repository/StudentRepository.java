package com.arun.springsecuritycorecontinued.repository;

import com.arun.springsecuritycorecontinued.domain.StudentDomain;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author arun on 9/19/20
 */
public interface StudentRepository extends PagingAndSortingRepository<StudentDomain, Integer> {
    List<StudentDomain> findByName(String name);
}
