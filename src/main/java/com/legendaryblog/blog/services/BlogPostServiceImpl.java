package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.BlogPostDTO;
import com.legendaryblog.blog.dtos.CategoryDTO;
import com.legendaryblog.blog.entities.BlogPost;
import com.legendaryblog.blog.entities.Category;
import com.legendaryblog.blog.exceptions.ResourceNotFoundException;
import com.legendaryblog.blog.mappers.BlogPostMappers;
import com.legendaryblog.blog.mappers.CategoryMappers;
import com.legendaryblog.blog.repositories.BlogPostRepository;
import com.legendaryblog.blog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        Category category = categoryRepository.findById(blogPostDTO.getCategoryDTO().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        blogPost.setCategory(category);


        blogPost.setCoverImageUrl(imageUrl);

        BlogPost savedPost = blogPostRepository.save(blogPost);
        return BlogPostMappers.mapBlogPostToDTO(savedPost);
    }

    @Override
    public BlogPostDTO fetchBlogPost(UUID id) {
        BlogPost existingBp = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found."));

        return BlogPostMappers.mapBlogPostToDTO(existingBp);
    }

    @Override
    public List<BlogPostDTO> fetchBlogPosts() {

        List<BlogPost> posts = blogPostRepository.findAll();

        List<BlogPostDTO> postsDTO = new ArrayList<>();

        for(BlogPost post : posts){
            ;
            postsDTO.add(BlogPostMappers.mapBlogPostToDTO(post));
        }

        return postsDTO;
    }

    @Override
    public BlogPostDTO patchPost(UUID id, BlogPostDTO updateDTO) {

        BlogPost existingBp = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found."));

        if ( updateDTO.getTitle() != null) existingBp.setTitle(updateDTO.getTitle());
        if (updateDTO.getDescription() != null) existingBp.setDescription(updateDTO.getDescription());
        if (updateDTO.getContent() != null) existingBp.setContent(updateDTO.getContent());
        if (updateDTO.getPublished() != null) existingBp.setPublished(updateDTO.getPublished());
        if (updateDTO.getFeatured() != null) existingBp.setFeatured(updateDTO.getFeatured());
        if (updateDTO.getCoverImageUrl() != null) existingBp.setCoverImageUrl(updateDTO.getCoverImageUrl());


        if (updateDTO.getCategoryDTO() != null) {
            Category category = categoryRepository.findById(updateDTO.getCategoryDTO().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingBp.setCategory(category);
        }

        BlogPostDTO updatedBp = BlogPostMappers.mapBlogPostToDTO(existingBp);

        blogPostRepository.save(existingBp);

        return updatedBp;
    }

    @Override
    public BlogPostDTO deleteBlogPost(UUID id) {
        BlogPost post = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BlogPost not found"));
        blogPostRepository.deleteById(id);

        return BlogPostMappers.mapBlogPostToDTO(post);
    }


}
