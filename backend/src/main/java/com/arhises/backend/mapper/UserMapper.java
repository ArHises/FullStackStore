package com.arhises.backend.mapper;

import com.arhises.backend.dto.UserDto;
import com.arhises.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // mapping methods here

    UserDto toDto(User user);
}
