package com.legendaryblog.blog.controllers;

import com.legendaryblog.blog.dtos.BlogPostDTO;
import com.legendaryblog.blog.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class BlogPostController {

    @Autowired
    BlogPostService blogPostService;

    @PostMapping(value = "/blogpost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogPostDTO> createBlogPost(
            @RequestPart("post") BlogPostDTO blogPostDTO,
            @RequestPart("image")MultipartFile image
            ) throws IOException{
        BlogPostDTO createdPost = blogPostService.createBlogPost(blogPostDTO,image);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}
