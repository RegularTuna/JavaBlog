package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.UserDTO;
import com.legendaryblog.blog.entities.User;
import com.legendaryblog.blog.exceptions.ConflictException;
import com.legendaryblog.blog.exceptions.ResourceNotFoundException;
import com.legendaryblog.blog.mappers.UserMapper;
import com.legendaryblog.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDTO register(UserDTO userDTO) {

        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());

        if(existingUser.isPresent())
            throw new ConflictException("User alreadyExists");

        User newUser = UserMapper.mapToEntity(userDTO);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return UserMapper.mapToDTO(newUser);
    }
}
