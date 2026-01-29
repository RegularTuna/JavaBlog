package com.legendaryblog.blog.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;

    private String username;
    private String password;
}
