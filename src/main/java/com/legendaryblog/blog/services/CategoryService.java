package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public CategoryDTO createCategory(CategoryDTO categoryDTO);
    public List<CategoryDTO> fetchCategories();
    public CategoryDTO updateCategory(CategoryDTO categoryDTO);
    public CategoryDTO deleteCategory(Integer id);
}
