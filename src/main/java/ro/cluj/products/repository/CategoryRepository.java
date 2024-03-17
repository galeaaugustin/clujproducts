package ro.cluj.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cluj.products.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentId(Long pid);   // get category by parent
}