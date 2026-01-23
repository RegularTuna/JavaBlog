package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.BlogPostDTO;
import com.legendaryblog.blog.entities.BlogPost;
import com.legendaryblog.blog.entities.Category;
import com.legendaryblog.blog.mappers.BlogPostMappers;
import com.legendaryblog.blog.repositories.BlogPostRepository;
import com.legendaryblog.blog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BlogPostServiceImpl implements BlogPostService{

    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private FileUploadService fileUploadService;


    @Override
    public BlogPostDTO createBlogPost(BlogPostDTO blogPostDTO, MultipartFile image) throws IOException {

        String imageUrl = fileUploadService.uploadImage(image);

        BlogPost blogPost = BlogPostMappers.mapToEntity(blogPostDTO);

        Category category = categoryRepository.findById(blogPostDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        blogPost.setCategory(category);


        blogPost.setCoverImageUrl(imageUrl);




        BlogPost savedPost = blogPostRepository.save(blogPost);
        return BlogPostMappers.mapBlogPostToDTO(savedPost);
    }
}
