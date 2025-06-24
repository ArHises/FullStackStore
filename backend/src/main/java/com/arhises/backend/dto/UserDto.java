package com.arhises.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto { // the fields we want to transfer between app layers
    private Long id;
    private String name;
    private String email;
}
