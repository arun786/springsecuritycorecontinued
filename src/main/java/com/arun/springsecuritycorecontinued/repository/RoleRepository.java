package com.arun.springsecuritycorecontinued.repository;

import com.arun.springsecuritycorecontinued.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * @author arun on 9/19/20
 */

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
