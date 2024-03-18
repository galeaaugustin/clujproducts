package ro.cluj.products.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.cluj.products.dto.CategoryDto;
import ro.cluj.products.entity.Category;
import ro.cluj.products.exception.ResourceNotFoundException;
import ro.cluj.products.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryDto addCategory(CategoryDto categoryDto) {
        // convert CategoryDto into Category Jpa entity
        var category = modelMapper.map(categoryDto, Category.class);
        // Category Jpa entity
        var savedCategory = categoryRepository.save(category);
        // Convert saved Category Jpa entity object into CategoryDto object
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    public CategoryDto getCategory(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id:" + id));
        return modelMapper.map(category, CategoryDto.class);
    }

    public List<CategoryDto> getAllCategories() {
        var categories = categoryRepository.findAll();
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .sorted(Comparator.comparing(CategoryDto::getName))
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getAllCategoriesByParentId(Long pid) {
        var categories = categoryRepository.findByParentId(pid);
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .sorted(Comparator.comparing(CategoryDto::getName))
                .collect(Collectors.toList());
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id));
        category.setName(categoryDto.getName());
        category.setParentId(categoryDto.getParentId());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    public void deleteCategory(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id));
        categoryRepository.deleteById(id);
        logger.info("Category with id was deleted " + category.getId());
    }
}
