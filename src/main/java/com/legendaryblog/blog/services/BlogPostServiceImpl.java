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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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

        if(blogPostDTO.getPublished() == true){
            blogPost.setPublishDate(LocalDateTime.now());
        }

        BlogPost savedPost = blogPostRepository.save(blogPost);

        BlogPostDTO result = BlogPostMappers.mapBlogPostToDTO(savedPost);

        result.setCategoryDTO(CategoryMappers.mapToDTO(category));

        return result;
    }

    @Override
    public BlogPostDTO fetchBlogPost(UUID id) {
        BlogPost existingBp = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found."));

        return BlogPostMappers.mapBlogPostToDTO(existingBp);
    }

    @Override
    public Map<String, Object> fetchBlogPosts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("publishDate").descending());

        Page<BlogPost> postPage = blogPostRepository.findAll(pageable);

        List<BlogPostDTO> postsDTO = new ArrayList<>();

        for(BlogPost post : postPage.getContent()){
            ;
            postsDTO.add(BlogPostMappers.mapBlogPostToDTO(post));
        }

        Map<String,Object> response = new HashMap<>();

        response.put("posts",postsDTO);
        response.put("currentPage", postPage.getNumber());
        response.put("totalItems", postPage.getTotalElements());
        response.put("totalPages", postPage.getTotalPages());
        return response;
    }

    @Override
    public BlogPostDTO patchPost(UUID id, BlogPostDTO updateDTO, MultipartFile image) throws IOException {

        BlogPost existingBp = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found."));

        if (updateDTO.getTitle() != null && !Objects.equals(updateDTO.getTitle(), existingBp.getTitle())) {
            existingBp.setTitle(updateDTO.getTitle());
        }

        if (updateDTO.getDescription() != null && !Objects.equals(updateDTO.getDescription(), existingBp.getDescription())) {
            existingBp.setDescription(updateDTO.getDescription());
        }

        if (updateDTO.getContent() != null && !Objects.equals(updateDTO.getContent(), existingBp.getContent())) {
            existingBp.setContent(updateDTO.getContent());
        }

        if (updateDTO.getPublished() != null && !Objects.equals(updateDTO.getPublished(), existingBp.getPublished())) {
            existingBp.setPublished(updateDTO.getPublished());
        }

        if (updateDTO.getFeatured() != null && !Objects.equals(updateDTO.getFeatured(), existingBp.getFeatured())) {
            existingBp.setFeatured(updateDTO.getFeatured());
        }

        if (!image.isEmpty()) {
            String imageUrl = fileUploadService.uploadImage(image);
            existingBp.setCoverImageUrl(imageUrl);
        }

        if (updateDTO.getCategoryDTO() != null && updateDTO.getCategoryDTO().getId() != null) {
            if (!Objects.equals(updateDTO.getCategoryDTO().getId(), existingBp.getCategory().getId())) {
                Category newCategory = categoryRepository.findById(updateDTO.getCategoryDTO().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                existingBp.setCategory(newCategory);
            }
        }

        if( existingBp.getPublishDate() == null && existingBp.getPublished() == true){
            existingBp.setPublishDate(LocalDateTime.now());
        }

        blogPostRepository.save(existingBp);

        BlogPostDTO updatedBp = BlogPostMappers.mapBlogPostToDTO(blogPostRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post not found")));
        return updatedBp;
    }

    @Override
    public BlogPostDTO deleteBlogPost(UUID id) {
        BlogPost post = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BlogPost not found"));
        blogPostRepository.deleteById(id);

        return BlogPostMappers.mapBlogPostToDTO(post);
    }


}
