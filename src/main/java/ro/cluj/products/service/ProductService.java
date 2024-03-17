package ro.cluj.products.service;

import ro.cluj.products.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    Optional<ProductDto> getProductById(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductsByCategoryId(Long categoryId);
    ProductDto updateProduct(ProductDto productDto, Long id);
    void deleteProduct(Long id);
}

