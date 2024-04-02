package com.springboot.blog.service;

import com.springboot.blog.payload.CategoryDto;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategory();

    CategoryDto updateCategory(CategoryDto categoryDto);

    String deleteCategory(Long id);
}
