# springsecuritycorecontinued

Basic Spring security continued from


Here we will be using Database to store the details of the user

![ER Diagram](https://github.com/arun786/springsecuritycorecontinued/blob/master/src/main/resources/ErDiagram.png)

Data details in the database 

![Data](https://github.com/arun786/springsecuritycorecontinued/blob/master/src/main/resources/dbscreenshot.png)


The Data which is loaded ....


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


Web security configured will be as below.

This is java based Global Method Security

    package com.arun.springsecuritycorecontinued.security;
    
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
    import org.springframework.security.crypto.password.PasswordEncoder;
    
    /**
     * @author arun on 9/18/20
     */
    
    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorize -> {
                        authorize
                                .antMatchers("/h2-console/**").permitAll();
                    })
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().and()
                    .httpBasic();
    
            http.csrf().disable();
            http.headers().frameOptions().sameOrigin();
        }
    
        @Bean
        public PasswordEncoder passwordEncoder() {
            return StudentPasswordEncoderFactory.createDelegatingPasswordEncoder();
        }
    
    }


Authentication for the user based on the UserDetailsService


    package com.arun.springsecuritycorecontinued.security;
    
    import com.arun.springsecuritycorecontinued.domain.Authority;
    import com.arun.springsecuritycorecontinued.domain.UserDomain;
    import com.arun.springsecuritycorecontinued.repository.UserRepository;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;
    
    import java.util.Collection;
    import java.util.HashSet;
    import java.util.Set;
    import java.util.stream.Collectors;
    
    /**
     * @author arun on 9/19/20
     */
    
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class JpaUserDetailsService implements UserDetailsService {
    
        private final UserRepository userRepository;
    
    
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserDomain user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User name : " + username + " not found"));
    
            return new User(user.getUsername(), user.getPassword(), user.getEnabled(),user.getAccountNotExpired(),user.getCredentialsNotExpired(),user.getAccountNotLocked(), convertToSpringAuthorities(user.getAuthorities()));
        }
    
        private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
            if (authorities != null && authorities.size() > 0) {
                return authorities.stream()
                        .map(Authority::getPermission)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
            }
    
            return new HashSet<>();
        }
    }


The below defined in controller


    package com.arun.springsecuritycorecontinued.controller;
    
    import com.arun.springsecuritycorecontinued.model.Student;
    import com.arun.springsecuritycorecontinued.service.StudentService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    
    /**
     * @author arun on 9/19/20
     */
    
    @RestController
    public class StudentController {
    
        private final StudentService studentService;
    
        @Autowired
        public StudentController(StudentService studentService) {
            this.studentService = studentService;
        }
    
    
        @PreAuthorize("hasAuthority('student.read')")
        @GetMapping("/v1/student")
        public ResponseEntity<List<Student>> getStudent(@RequestParam String name) {
            List<Student> student = studentService.getStudent(name);
            return ResponseEntity.ok(student);
        }
    
    
        @PostMapping("/v1/student")
        @PreAuthorize("hasAuthority('student.create')")
        public ResponseEntity<Student> createStudent(@RequestBody Student student) {
            Student saved = studentService.createStudent(student);
            return ResponseEntity.ok(saved);
        }
    
        @PreAuthorize("hasAuthority('student.delete')")
        @DeleteMapping("/v1/student")
        public ResponseEntity<HttpStatus> deleteStudent(@RequestBody Student student) {
            studentService.deleteStudent(student);
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    
        @PreAuthorize("hasAuthority('student.update')")
        @PutMapping("/v1/student")
        public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
            Student updatedStudent = studentService.updateStudent(student);
            return ResponseEntity.ok(updatedStudent);
        }
    
    }
