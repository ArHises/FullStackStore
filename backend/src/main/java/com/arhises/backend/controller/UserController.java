package com.arhises.backend.controller;

import com.arhises.backend.dto.UserDto;
import com.arhises.backend.entity.User;
import com.arhises.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map( user -> new UserDto(user.getId()
                        , user.getName()
                        , user.getEmail()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (!checkValid(user)) {
            return ResponseEntity.badRequest().body("Invalid user data or email already exists");
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    private boolean checkValid(User user) {
        if (user.getName() == null || user.getName().isEmpty() ||
            user.getEmail() == null || user.getEmail().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty()) {
            return false;
        }
        // Check if email already exists
        boolean emailExists = ((Collection<User>) userRepository.findAll())
            .stream()
            .anyMatch(u -> u.getEmail().equals(user.getEmail()));
        return !emailExists;
    }

}
