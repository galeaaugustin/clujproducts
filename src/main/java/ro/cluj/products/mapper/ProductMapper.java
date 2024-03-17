package ro.cluj.products.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.cluj.products.dto.ProductDto;
import ro.cluj.products.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper( ProductMapper.class );

    @Mapping( source = "categoryId", target = "category.id")
    Product fromProductDto(ProductDto productDto);

    @InheritInverseConfiguration
    ProductDto fromProduct(Product product);

}
