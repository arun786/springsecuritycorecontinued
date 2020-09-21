package com.arun.springsecuritycorecontinued.loader;

import com.arun.springsecuritycorecontinued.domain.Authority;
import com.arun.springsecuritycorecontinued.domain.Role;
import com.arun.springsecuritycorecontinued.domain.UserDomain;
import com.arun.springsecuritycorecontinued.repository.AuthorityRepository;
import com.arun.springsecuritycorecontinued.repository.RoleRepository;
import com.arun.springsecuritycorecontinued.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author arun on 9/19/20
 */

@Configuration
public class UserLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoader(AuthorityRepository authorityRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        Authority createStudent = authorityRepository.save(new Authority().setPermission("student.create"));
        Authority readStudent = authorityRepository.save(new Authority().setPermission("student.read"));
        Authority updateStudent = authorityRepository.save(new Authority().setPermission("student.update"));
        Authority deleteStudent = authorityRepository.save(new Authority().setPermission("student.delete"));


        Role adminRole = roleRepository.save(new Role().setName("ADMIN"));
        Role customerRole = roleRepository.save(new Role().setName("CUSTOMER"));
        Role userRole = roleRepository.save(new Role().setName("USER"));

        adminRole.setAuthorities(new HashSet<>(Set.of(createStudent, readStudent, updateStudent, deleteStudent)));
        customerRole.setAuthorities(new HashSet<>(Set.of(readStudent)));
        userRole.setAuthorities(new HashSet<>(Set.of(readStudent)));

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        userRepository.save(new UserDomain().setUsername("spring").setPassword(passwordEncoder.encode("guru")).setRoles(Set.of(adminRole)));
        userRepository.save(new UserDomain().setUsername("user").setPassword(passwordEncoder.encode("password")).setRoles(Set.of(userRole)));
        userRepository.save(new UserDomain().setUsername("tiger").setPassword(passwordEncoder.encode("scott")).setRoles(Set.of(customerRole)));
    }
}
