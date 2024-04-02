package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private ModelMapper mapper;
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper mapper, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        var category = mapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
        var newCategoryDto = mapper.map(category, CategoryDto.class);

        return newCategoryDto;
    }

    @Override
    public CategoryDto getCategory(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",Long.toString(id)));

        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        var categories = categoryRepository.findAll().
                stream().map((c)->mapper.map(c,CategoryDto.class))
                .collect(Collectors.toList());

        return categories;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        var category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",Long.toString(categoryDto.getId())));

       category.setName(categoryDto.getName());
       category.setDescription(categoryDto.getDescription());

       categoryRepository.save(category);

       return mapper.map(category,CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long id) {

        var category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",Long.toString(id)));

        categoryRepository.delete(category);

        return "Category delete success !";
    }
}
