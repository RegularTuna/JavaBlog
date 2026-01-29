package com.legendaryblog.blog.services;

import com.legendaryblog.blog.entities.User;
import com.legendaryblog.blog.exceptions.ResourceNotFoundException;
import com.legendaryblog.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = Optional.ofNullable(repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found")));


        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),user.get().getPassword(), new ArrayList<>());

    }



}
