package com.legendaryblog.blog.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime publishDate;
    private Boolean published;
    private Boolean featured;
    private String coverImageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
