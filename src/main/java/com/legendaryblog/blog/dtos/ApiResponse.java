package com.legendaryblog.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    public String message;
    private boolean status;


}
