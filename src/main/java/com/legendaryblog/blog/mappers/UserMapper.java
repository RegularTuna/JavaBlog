package com.legendaryblog.blog.mappers;

import com.legendaryblog.blog.dtos.UserDTO;
import com.legendaryblog.blog.entities.User;

public class UserMapper {

    public static UserDTO mapToDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        return dto;
    }


    public static User mapToEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        return user;
    }
}
