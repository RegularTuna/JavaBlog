package com.legendaryblog.blog.mappers;

import com.legendaryblog.blog.dtos.BlogPostDTO;
import com.legendaryblog.blog.entities.BlogPost;

public class BlogPostMappers {

    public static BlogPostDTO mapBlogPostToDTO(BlogPost bp){
        if (bp == null) return null;
        BlogPostDTO dto = new BlogPostDTO();
        dto.setId(bp.getId());
        dto.setTitle(bp.getTitle());
        dto.setDescription(bp.getDescription());
        dto.setContent(bp.getContent());
        dto.setPublishDate(bp.getPublishDate());
        dto.setPublished(bp.getPublished());
        dto.setFeatured(bp.getFeatured());


        return dto;
    }

    public static BlogPost mapToEntity(BlogPostDTO dto) {
        if (dto == null) return null;

        BlogPost bp = new BlogPost();
        bp.setId(dto.getId());
        bp.setTitle(dto.getTitle());
        bp.setDescription(dto.getDescription());
        bp.setContent(dto.getContent());
        bp.setPublishDate(dto.getPublishDate());
        bp.setPublished(dto.getPublished());
        bp.setFeatured(dto.getFeatured());

        return bp;
    }
}
