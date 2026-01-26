package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.BlogPostDTO;
import com.legendaryblog.blog.dtos.CategoryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface BlogPostService {
    public BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO, MultipartFile image)throws IOException;
    public BlogPostDTO fetchBlogPost(UUID id);
    public List<BlogPostDTO> fetchBlogPosts();
    public BlogPostDTO patchPost(UUID id, BlogPostDTO updateDTO);
    public BlogPostDTO deleteBlogPost(UUID id);
}
