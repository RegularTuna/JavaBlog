package com.legendaryblog.blog.services;

import com.legendaryblog.blog.dtos.CategoryDTO;
import com.legendaryblog.blog.entities.Category;
import com.legendaryblog.blog.exceptions.ConflictException;
import com.legendaryblog.blog.exceptions.ResourceNotFoundException;
import com.legendaryblog.blog.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategory_shouldCreateCategory_WhenDataIsValid() {
        CategoryDTO request = createDTO(null,"Test1");

        when(categoryRepository.findCategoryByName("Test1")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> {
            Category c = i.getArgument(0);
            c.setId(1);
            return c;
        });

        CategoryDTO result = categoryService.createCategory(request);

        assertNotNull(result.getId());
        assertEquals(1, result.getId());
        assertEquals("Test1", result.getName());
        verify(categoryRepository,times(1)).save(any(Category.class));
    }

    @Test
    void createCategory_shouldThrowConflictException_WhenNameAlreadyTaken(){
        CategoryDTO request = createDTO(null, "Test1");
        Category existing = createCategory(1, "Test1");

        when(categoryRepository.findCategoryByName("Test1")).thenReturn(Optional.of(existing));

        assertThrows(ConflictException.class, () -> categoryService.createCategory(request));

        verify(categoryRepository, never()).save(any());
    }


    @Test
    void fetchCategories_shouldReturnListOfCategoryDTO() {
        Category category1 = createCategory(1, "Test1");
        Category category2 = createCategory(2, "Test2");

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        List<CategoryDTO> result = categoryService.fetchCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test1", result.get(0).getName());
        assertEquals("Test2", result.get(1).getName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void fetchCategoryById_shouldReturnCategory_whenExists() {
        Integer request = 1;
        Category existing = createCategory(1, "Test1");

        when(categoryRepository.findCategoriesById(1)).thenReturn(Optional.of(existing));

        CategoryDTO result = categoryService.fetchCategoryById(1);

        assertEquals(1, result.getId());
        assertEquals("Test1", result.getName());

        verify(categoryRepository, times(1)).findCategoriesById(1);

    }

    @Test
    void fetchCategoryById_shouldThrowResourceNotFoundException_whenCategoryDoesNotExist(){
        Integer request = 99;

        when(categoryRepository.findCategoriesById(request)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.fetchCategoryById(request));

        verify(categoryRepository, times(1)).findCategoriesById(request);
    }

    @Test
    void updateCategory_shouldReturnUpdatedCategory_whenDataIsValid() {

        CategoryDTO request = createDTO(1, "Test2");
        Category existing = createCategory(1, "Test1");

        when(categoryRepository.findCategoriesById(request.getId())).thenReturn(Optional.of(existing));
        when(categoryRepository.findCategoryByName(request.getName())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        CategoryDTO result = categoryService.updateCategory(request);

        assertNotNull(result);
        assertEquals(result.getId(), request.getId());
        assertEquals(result.getName(), request.getName());

        verify(categoryRepository, times(1)).findCategoriesById(request.getId());
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void updateCategory_shouldThrowResourceNotFoundException_whenIdIsDoesNotExist() {

        CategoryDTO request = createDTO(99, "Test1");


        when(categoryRepository.findCategoriesById(request.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(request));

        verify(categoryRepository, times(1)).findCategoriesById(request.getId());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategory_shouldThrowConflictException_whenNameAlreadyExists() {

        CategoryDTO request = createDTO(1, "Test2");
        Category existing = createCategory(1, "Test1");
        Category existingWithSameName = createCategory(2, "Test2");

        when(categoryRepository.findCategoriesById(request.getId())).thenReturn(Optional.of(existing));
        when(categoryRepository.findCategoryByName(request.getName())).thenReturn(Optional.of(existingWithSameName));


        assertThrows(ConflictException.class, () -> categoryService.updateCategory(request));

        verify(categoryRepository, times(1)).findCategoriesById(request.getId());
        verify(categoryRepository, times(1)).findCategoryByName(request.getName());
        verify(categoryRepository, never()).save(any());
    }


    @Test
    void deleteCategory_shouldReturnDeletedCategory_whenCategoryExists() {
        Integer request = 1;
        Category existing = createCategory(request, "Test1");

        when(categoryRepository.findCategoriesById(request)).thenReturn(Optional.of(existing));

        CategoryDTO result = categoryService.deleteCategory(request);

        assertNotNull(result);
        assertEquals(request, result.getId());
        assertEquals(existing.getName(), result.getName());

        verify(categoryRepository, times(1)).deleteById(request);
    }

    @Test
    void deleteCategory_shouldThrowResourceNotFoundException_whenCategoryDoesNotExist(){
        Integer request = 1;

        when(categoryRepository.findCategoriesById(request)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(request));

        verify(categoryRepository, never()).deleteById(request);

    }

    //Helper methods
    private Category createCategory(Integer id, String name){
        Category category = new Category();
        category.setName(name);
        category.setId(id);
        return category;
    }

    private CategoryDTO createDTO(Integer id, String name) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}