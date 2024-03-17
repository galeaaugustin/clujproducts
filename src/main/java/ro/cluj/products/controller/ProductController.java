package ro.cluj.products.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.cluj.products.dto.ProductDto;
import ro.cluj.products.service.ProductService;
import ro.cluj.products.utils.ApiResourcePath;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> addProduct( @Valid @RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id){
        return new ResponseEntity<>(productService.getProductById(id).get(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value = ApiResourcePath.PATH_CATEGORY + "/{categoryId:[0-9]+}" , method = RequestMethod.GET)
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct( @Valid @RequestBody ProductDto productDto, @PathVariable("id") Long id){
        return ResponseEntity.ok( productService.updateProduct(productDto, id) );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully!.");
    }
}
