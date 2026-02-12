package com.legendaryblog.blog.repositories;

import com.legendaryblog.blog.entities.BlogPost;

import com.legendaryblog.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {



}
