package jkucaba.springstore.controller;

import jkucaba.springstore.entity.User;
import jkucaba.springstore.model.ProductDTO;
import jkucaba.springstore.service.ProductService;
import jkucaba.springstore.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SessionService sessionService;
    private static final String PRODUCT_PATH = "/api/v1/product";

    @GetMapping(PRODUCT_PATH)
    public List<ProductDTO> listProducts(
            @RequestHeader("X-SESSION-ID") UUID sessionId
    ){
        User user = sessionService.validateSession(sessionId);

        return productService.listProducts();
    }

}
