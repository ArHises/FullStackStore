package com.arhises.backend.repository;

import com.arhises.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User, Long> {
}
