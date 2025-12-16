package jkucaba.springstore.bootstrap;

import jkucaba.springstore.entity.Product;
import jkucaba.springstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        loadProductData();
    }

    private void loadProductData(){
        if(productRepository.count() == 0){
            Product product1 = Product.builder()
                    .title("Nail Gun")
                    .available(8)
                    .price(new BigDecimal("123.99")).build();

            Product product2 = Product.builder()
                    .title("Hammer")
                    .available(120)
                    .price(new BigDecimal("12.99")).build();

            Product product3 = Product.builder()
                    .title("Blue Paint")
                    .available(35)
                    .price(new BigDecimal("20.31")).build();

            Product product4 = Product.builder()
                    .title("Paint Brush")
                    .available(122)
                    .price(new BigDecimal("5.59")).build();

            Product product5 = Product.builder()
                    .title("Ladder")
                    .available(25)
                    .price(new BigDecimal("45.00")).build();

            Product product6 = Product.builder()
                    .title("Nail")
                    .available(500)
                    .price(new BigDecimal("1.22")).build();

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
            productRepository.save(product6);
        }
    }
}
