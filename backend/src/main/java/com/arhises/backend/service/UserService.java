package com.arhises.backend.service;

import com.arhises.backend.dto.UserDto;
import com.arhises.backend.entity.UserEntity;
import com.arhises.backend.mapper.UserMapper;
import com.arhises.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.arhises.backend.mapper.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserMapper userMapper;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserEntity register(UserEntity user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verify(UserEntity user) {
        Authentication authentication =
                authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }

        return "failed to login";
    }

    public Iterable<UserDto> getAllUsers(String sort){
        return userRepository
                .findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public ResponseEntity<UserDto> getUserById(Long id){
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
