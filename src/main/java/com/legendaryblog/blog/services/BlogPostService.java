package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.BlogPostDTO;
import com.legendaryblog.blog.dtos.CategoryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BlogPostService {
    public BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO, MultipartFile image)throws IOException;
    public BlogPostDTO fetchBlogPost(UUID id);
    public Map<String, Object> fetchBlogPosts(int page, int size);
    public BlogPostDTO patchPost(UUID id, BlogPostDTO updateDTO, MultipartFile image) throws IOException;
    public BlogPostDTO deleteBlogPost(UUID id);
}
