package com.legendaryblog.blog.controllers;

import com.legendaryblog.blog.dtos.CategoryDTO;
import com.legendaryblog.blog.services.CategoryService;
import com.legendaryblog.blog.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories(){

        List<CategoryDTO> categories = categoryService.fetchCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer id){

        CategoryDTO categoryDTO = categoryService.fetchCategoryById(id);

        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PutMapping("/category")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO){

        CategoryDTO dto = categoryService.updateCategory(categoryDTO);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Integer id){

        CategoryDTO categoryDTO = categoryService.deleteCategory(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
}
