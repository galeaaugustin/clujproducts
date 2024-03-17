package ro.cluj.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cluj.products.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long cid);   // get product by category
}
