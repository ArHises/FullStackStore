package com.arhises.backend.controller;

import com.arhises.backend.dto.UserDto;
import com.arhises.backend.entity.UserEntity;
import com.arhises.backend.mapper.UserMapper;
import com.arhises.backend.repository.UserRepository;
import com.arhises.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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

//        System.out.println(user.getUsername() + " just logged in");
//        return user.getUsername() + " just logged in";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user) {
//        if (!checkValid(user)) {
//            return ResponseEntity.badRequest().body("Invalid user data or email already exists");
//        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    private boolean checkValid(UserEntity user) {
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
            user.getEmail() == null || user.getEmail().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty()) {
            return false;
        }
        // Check if email already exists
        boolean emailExists = ((Collection<UserEntity>) userRepository.findAll())
            .stream()
            .anyMatch(u -> u.getEmail().equals(user.getEmail()));
        return !emailExists;
    }

}
