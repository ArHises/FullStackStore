package com.arhises.backend.repository;

import com.arhises.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


// JpaRepository for DTO and Lists
public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    // searching logic if needed... example: public User findByEmail(String email);
    // By default, CrudRepository
    //  userRepository.findAll() — get all users
    //  userRepository.findById(id) — get a user by ID
    //  userRepository.save(user) — add or update a user
    //  userRepository.deleteById(id) — delete a user by ID

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
