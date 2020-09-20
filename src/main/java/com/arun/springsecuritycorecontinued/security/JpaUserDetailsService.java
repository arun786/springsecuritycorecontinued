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
