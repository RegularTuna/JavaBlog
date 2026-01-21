package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.CategoryDTO;
import com.legendaryblog.blog.entities.Category;
import com.legendaryblog.blog.mappers.CategoryMappers;
import com.legendaryblog.blog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = CategoryMappers.mapToEntity(categoryDTO);
        //Optional<Category>

        return null;
    }

    @Override
    public List<CategoryDTO> fetchCategories() {
        return List.of();
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public CategoryDTO deleteCategory(Integer id) {
        return null;
    }
}
