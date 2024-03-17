package ro.cluj.products.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.cluj.products.dto.ProductDto;
import ro.cluj.products.exception.ResourceNotFoundException;
import ro.cluj.products.mapper.ProductMapper;
import ro.cluj.products.repository.CategoryRepository;
import ro.cluj.products.repository.ProductRepository;
import ro.cluj.products.service.ProductService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        // convert ProductDto into Product Jpa entity using mapstruct
        var product = ProductMapper.MAPPER.fromProductDto(productDto);
        /*
        var category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + product.getCategory().getId()));
        product.setCategory(category);
        */
        // Product Jpa entity
        var savedProduct = productRepository.save(product);
        // Convert saved Product Jpa entity object into productDto object
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return Optional.ofNullable(modelMapper.map(product, ProductDto.class));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        var allProducts = productRepository.findAll();
        return allProducts.stream().map((product) -> modelMapper.map(product, ProductDto.class))
                .sorted(Comparator.comparing(ProductDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByCategoryId(Long categoryId) {
        var categories = productRepository.findByCategoryId(categoryId);
        return categories.stream().map((product) -> modelMapper.map(product, ProductDto.class))
                .sorted(Comparator.comparing(ProductDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
        var category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + productDto.getCategoryId()));

        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setPrice(product.getPrice());

        var updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
        productRepository.deleteById(id);
    }
}
