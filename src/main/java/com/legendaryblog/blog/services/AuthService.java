package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.UserDTO;
import com.legendaryblog.blog.entities.User;

public interface AuthService {

    public UserDTO register (UserDTO userDTO);

}
