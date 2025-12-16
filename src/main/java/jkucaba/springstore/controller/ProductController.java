package jkucaba.springstore.controller;

import jkucaba.springstore.model.ProductDTO;
import jkucaba.springstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final String PRODUCT_PATH = "/api/v1/product";

    @GetMapping(PRODUCT_PATH)
    public List<ProductDTO> listProducts(){
        return productService.listProducts();
    }

}
