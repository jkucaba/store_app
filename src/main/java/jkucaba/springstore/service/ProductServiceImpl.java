package jkucaba.springstore.service;

import jkucaba.springstore.mapper.ProductMapper;
import jkucaba.springstore.model.ProductDTO;
import jkucaba.springstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> listProducts() {
        return productRepository.findAll()
                .stream().map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }
}
