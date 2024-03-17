package ro.cluj.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parentId", nullable = false, length = 16, columnDefinition = "BIGINT")
    private Long parentId;      // parent Id

    @Column(name = "name", nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "description", nullable = false, length = 255, columnDefinition = "VARCHAR(255)")
    private String description;

    @OneToMany(mappedBy="category")
    private Set<Product> product;

}
