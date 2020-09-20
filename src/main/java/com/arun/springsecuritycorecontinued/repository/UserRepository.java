package com.arun.springsecuritycorecontinued.repository;

import com.arun.springsecuritycorecontinued.domain.UserDomain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author arun on 9/19/20
 */
public interface UserRepository extends CrudRepository<UserDomain, Integer> {

    Optional<UserDomain> findByUsername(String name);
}
