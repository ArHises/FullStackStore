package com.arhises.backend.service;

import com.arhises.backend.entity.UserEntity;
import com.arhises.backend.entity.UserPrincipal;
import com.arhises.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = repo.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println(username + " not Found");
                    return new UsernameNotFoundException(username + " not Found");
                });

        System.out.println(user.getUsername() + "(" + user.getRole() + ") Connected");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );

    }
}