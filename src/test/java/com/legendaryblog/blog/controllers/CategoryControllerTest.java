package com.legendaryblog.blog.controllers;

import com.legendaryblog.blog.dtos.CategoryDTO;
import com.legendaryblog.blog.services.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import org.springframework.http.MediaType;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryServiceImpl categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp(){
        categoryDTO = new CategoryDTO(1, "Technology");
    }

    @Test
    void createCategory_shouldReturnCreated() throws Exception {
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Technology"));
    }

    @Test
    void getCategories_shouldReturnList() throws Exception{
        List<CategoryDTO> list = Arrays.asList(categoryDTO, new CategoryDTO(2, "Sports"));
        when(categoryService.fetchCategories()).thenReturn(list);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Technology"))
                .andExpect(jsonPath("$[1].name").value("Sports"));
    }

    @Test
    void getCategory_shouldReturnCategory_whenIdExists() throws Exception {
        when(categoryService.fetchCategoryById(1)).thenReturn(categoryDTO);

        // Updated for PathVariable: /api/category/{id}
        mockMvc.perform(get("/api/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Technology"));
    }

    @Test
    void updateCategory_shouldReturnUpdatedCategory() throws Exception {
        when(categoryService.updateCategory(any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(put("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Technology"));
    }

    @Test
    void deleteCategory_shouldReturnOk_whenIdExists() throws Exception {
        when(categoryService.deleteCategory(1)).thenReturn(categoryDTO);

        // Updated for PathVariable: /api/category/{id}
        mockMvc.perform(delete("/api/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}