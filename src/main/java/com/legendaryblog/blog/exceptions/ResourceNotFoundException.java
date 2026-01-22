package com.legendaryblog.blog.exceptions;

import lombok.NoArgsConstructor;

import java.io.Serial;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public  ResourceNotFoundException(String message){
        super(message);
    }
}
