package com.legendaryblog.blog.dtos;

import com.legendaryblog.blog.entities.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BlogPostDTO {
    private UUID id;

    private String title;
    private String description;

    private String content;

    private LocalDateTime publishDate;
    private Boolean published;
    private Boolean featured;
    private String coverImageUrl;
    private Integer categoryId;
}
