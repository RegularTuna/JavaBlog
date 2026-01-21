package com.legendaryblog.blog.mappers;

import com.legendaryblog.blog.dtos.CategoryDTO;
import com.legendaryblog.blog.entities.Category;

public class CategoryMappers {

    // Entity -> DTO
    public static CategoryDTO mapToDTO(Category category) {
        if (category == null) return null;

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }

    // DTO -> Entity
    public static Category mapToEntity(CategoryDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        return category;
    }
}
