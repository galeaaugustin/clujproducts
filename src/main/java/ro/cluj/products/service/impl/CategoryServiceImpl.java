package ro.cluj.products.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.cluj.products.dto.CategoryDto;
import ro.cluj.products.entity.Category;
import ro.cluj.products.exception.ResourceNotFoundException;
import ro.cluj.products.repository.CategoryRepository;
import ro.cluj.products.service.CategoryService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        // convert CategoryDto into Category Jpa entity
        var category = modelMapper.map(categoryDto, Category.class);
        // Category Jpa entity
        var savedCategory = categoryRepository.save(category);
        // Convert saved Category Jpa entity object into CategoryDto object
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id:" + id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        var categories = categoryRepository.findAll();
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .sorted(Comparator.comparing(CategoryDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getAllCategoriesByParentId(Long pid) {
        var categories = categoryRepository.findByParentId(pid);
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .sorted(Comparator.comparing(CategoryDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id));
        category.setName(categoryDto.getName());
        category.setParentId(categoryDto.getParentId());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id));
        categoryRepository.deleteById(id);
    }
}
