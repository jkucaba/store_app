package jkucaba.springstore.bootstrap;

import jkucaba.springstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BootstrapDataTest {

    BootstrapData bootstrapData;
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(productRepository);
    }

    @Test
    void runLoadsProductData() throws Exception {
        bootstrapData.run();
        assertThat(productRepository.count()).isEqualTo(6);
    }

}