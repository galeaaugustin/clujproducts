package ro.cluj.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.cluj.products.entity.Product;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("parentId")
    private Long parentId;  //parent id, zero is the root

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("productLists")
    private List<Product> productLists;
}