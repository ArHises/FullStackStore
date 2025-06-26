package com.arhises.backend.mapper;

import com.arhises.backend.dto.UserDto;
import com.arhises.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
