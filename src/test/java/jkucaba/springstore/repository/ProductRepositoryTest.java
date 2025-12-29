package jkucaba.springstore.repository;

import jakarta.validation.ConstraintViolationException;
import jkucaba.springstore.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void saveProduct_shouldFlushProductToRepository() {
        Product savedProduct = productRepository.save(Product.builder()
                .title("Test Product")
                .available(100)
                .price(BigDecimal.valueOf(19.99))
                .build()
        );

        productRepository.flush();

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertThat("Test Product").isEqualTo(savedProduct.getTitle());
        assertThat(100).isEqualTo(savedProduct.getAvailable());
        assertThat(BigDecimal.valueOf(19.99)).isEqualTo(savedProduct.getPrice());
    }

    @Test
    void saveProductWithInvalidName_shouldRespondWithError() {
        assertThrows(ConstraintViolationException.class, () -> {
            Product savedProduct = productRepository.save(
                    Product.builder()
                            .title("")
                            .available(100)
                            .price(BigDecimal.valueOf(19.99))
                            .build()
            );
            productRepository.flush();
        });
    }
}