package com.arhises.backend.controller;

import com.arhises.backend.entity.User;
import com.arhises.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/getUsers")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
}
