package com.legendaryblog.blog.controllers;

import com.legendaryblog.blog.dtos.BlogPostDTO;
import com.legendaryblog.blog.dtos.CategoryDTO;
import com.legendaryblog.blog.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/post/{id}")
    public ResponseEntity<BlogPostDTO> fetchBlogPost(@PathVariable UUID id){

        BlogPostDTO blogPostDTO = blogPostService.fetchBlogPost(id);

        return new ResponseEntity<>(blogPostDTO, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<BlogPostDTO>> fetchBlogPosts(){

        List<BlogPostDTO> blogPostDTOs = blogPostService.fetchBlogPosts();

        return new ResponseEntity<>(blogPostDTOs, HttpStatus.OK);
    }

    @PatchMapping("/post/{id}")
    public ResponseEntity<BlogPostDTO> patchPost(@PathVariable UUID id, @RequestBody BlogPostDTO blogPostDTO){
        BlogPostDTO updatedPost = blogPostService.patchPost(id,blogPostDTO);

        return new ResponseEntity<>(blogPostDTO, HttpStatus.OK);
    }


    @DeleteMapping("/post/{id}")
    public ResponseEntity<BlogPostDTO> deleteBlogPost(@PathVariable UUID id){

        BlogPostDTO blogpostDTO = blogPostService.deleteBlogPost(id);
        return new ResponseEntity<>(blogpostDTO, HttpStatus.OK);
    }
}
