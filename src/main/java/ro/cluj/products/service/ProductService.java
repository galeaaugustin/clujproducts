package ro.cluj.products.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.cluj.products.dto.ProductDto;
import ro.cluj.products.exception.ResourceNotFoundException;
import ro.cluj.products.mapper.ProductMapper;
import ro.cluj.products.repository.CategoryRepository;
import ro.cluj.products.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public ProductDto addProduct(ProductDto productDto) {
        // convert ProductDto into Product Jpa entity using mapstruct
        var product = ProductMapper.MAPPER.fromProductDto(productDto);
        // added logger
        logger.debug("Debug log message " + product.toString());
        logger.info("Info log message " + product);
        logger.error("Error log message " + product);
        // Product Jpa entity
        var savedProduct = productRepository.save(product);
        // Convert saved Product Jpa entity object into productDto object
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    public Optional<ProductDto> getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return Optional.ofNullable(modelMapper.map(product, ProductDto.class));
    }

    public List<ProductDto> getAllProducts() {
        var allProducts = productRepository.findAll();
        return allProducts.stream().map((product) -> modelMapper.map(product, ProductDto.class))
                .sorted(Comparator.comparing(ProductDto::getName))
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsByCategoryId(Long categoryId) {
        var categories = productRepository.findByCategoryId(categoryId);
        return categories.stream().map((product) -> modelMapper.map(product, ProductDto.class))
                .sorted(Comparator.comparing(ProductDto::getName))
                .collect(Collectors.toList());
    }

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

    public void deleteProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
        productRepository.deleteById(id);
        logger.info("Product with id was deleted " + product.getId());
    }
}
