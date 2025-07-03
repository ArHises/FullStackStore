package com.arhises.backend.controller;

import com.arhises.backend.dto.UserDto;
import com.arhises.backend.entity.UserEntity;
import com.arhises.backend.mapper.UserMapper;
import com.arhises.backend.repository.UserRepository;
import com.arhises.backend.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers( @RequestParam(required = false, defaultValue = "") String sort) {
        if (!Set.of("username", "email").contains(sort)){
            sort = "username";
        }
        return userRepository
                .findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserEntity user){
        return userService.verify(user);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public void adminOnlyMethod() {
        System.out.println("Admin only method");
    }

}
