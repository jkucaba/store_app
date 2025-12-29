package jkucaba.springstore.controller;

import jkucaba.springstore.model.ProductDTO;
import jkucaba.springstore.service.ProductService;
import jkucaba.springstore.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    ProductService productService;

    @MockitoBean
    SessionService sessionService;

    private static final String PRODUCT_PATH = "/api/v1/product";

    @Test
    void givenValidRequest_whenListProducts_shouldReturnProductList() throws Exception {
        given(productService.listProducts())
                .willReturn(
                        List.of(
                                new ProductDTO(
                                        UUID.randomUUID(),
                                        "Test Product",
                                        100,
                                        BigDecimal.valueOf(19.99)
                                )
                        )
                );

        mockMvc.perform(get(PRODUCT_PATH)
                        .header("X-SESSION-ID", UUID.randomUUID())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Product"))
                .andExpect(jsonPath("$[0].available").value(100))
                .andExpect(jsonPath("$[0].price").value(19.99));

    }

}