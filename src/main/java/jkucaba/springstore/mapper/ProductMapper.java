package jkucaba.springstore.mapper;

import jkucaba.springstore.entity.Product;
import jkucaba.springstore.model.ProductDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);
}
