package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.BlogPostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BlogPostService {
    public BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO, MultipartFile image)throws IOException;
}
