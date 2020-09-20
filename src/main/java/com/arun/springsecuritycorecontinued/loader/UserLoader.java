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

import java.util.Arrays;
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

    @Override
    public void run(String... args) throws Exception {

        Authority createStudent = new Authority().setPermission("student.create");
        Authority readStudent = new Authority().setPermission("student.read");
        Authority updateStudent = new Authority().setPermission("student.update");
        Authority deleteStudent = new Authority().setPermission("student.delete");

        authorityRepository.save(createStudent);
        authorityRepository.save(readStudent);
        authorityRepository.save(updateStudent);
        authorityRepository.save(deleteStudent);

        Role adminRole = new Role().setName("ADMIN");
        Role customerRole = new Role().setName("CUSTOMER");
        Role userRole = new Role().setName("USER");

        roleRepository.save(adminRole);
        roleRepository.save(customerRole);
        roleRepository.save(userRole);

        adminRole.setAuthorities(Set.of(createStudent,readStudent,updateStudent,deleteStudent));
        customerRole.setAuthorities(Set.of(readStudent));
        userRole.setAuthorities(Set.of(readStudent));

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        UserDomain spring = new UserDomain().setUsername("spring").setPassword(passwordEncoder.encode("guru")).setRoles(Set.of(adminRole));
        UserDomain user = new UserDomain().setUsername("user").setPassword(passwordEncoder.encode("password")).setRoles(Set.of(userRole));
        UserDomain scott = new UserDomain().setUsername("scott").setPassword(passwordEncoder.encode("tiger")).setRoles(Set.of(customerRole));

        userRepository.save(spring);
        userRepository.save(user);
        userRepository.save(scott);


    }
}
