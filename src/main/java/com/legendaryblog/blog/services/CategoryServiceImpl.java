package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.CategoryDTO;
import com.legendaryblog.blog.entities.Category;
import com.legendaryblog.blog.exceptions.ConflictException;
import com.legendaryblog.blog.exceptions.ResourceNotFoundException;
import com.legendaryblog.blog.mappers.CategoryMappers;
import com.legendaryblog.blog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO){

        Category category = CategoryMappers.mapToEntity(categoryDTO);
        Optional<Category> categoryFromDb= categoryRepository.findCategoryByName(categoryDTO.getName());

        if(categoryFromDb.isPresent())
            throw new ConflictException("Category name already exists");

        categoryRepository.save(category);

        return CategoryMappers.mapToDTO(category);
    }

    @Override
    public List<CategoryDTO> fetchCategories() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new ArrayList<>();

        for(Category category : categories){

            dtoList.add(CategoryMappers.mapToDTO(category));
        }
        return dtoList;
    }

    @Override
    public CategoryDTO fetchCategoryById(Integer id) {

        Optional<Category> category = categoryRepository.findCategoriesById(id);
        if(category.isEmpty()){
            throw new ResourceNotFoundException("No category found");
        }
        return CategoryMappers.mapToDTO(category.get());
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findCategoriesById(categoryDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if(!category.getName().equals(categoryDTO.getName())){
            Optional<Category> nameValidation = categoryRepository.findCategoryByName(categoryDTO.getName());
            if (!nameValidation.isEmpty()){
                throw new ConflictException("A category with that name already exists");
            }
        }


        category.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMappers.mapToDTO(updatedCategory);
    }

    @Override
    public CategoryDTO deleteCategory(Integer id) {
        Category category = categoryRepository.findCategoriesById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.deleteById(id);

        return CategoryMappers.mapToDTO(category);
    }
}
