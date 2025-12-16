CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    quantity INT NOT NULL,

    CONSTRAINT fk_cart_session
        FOREIGN KEY (session_id) REFERENCES sessions(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_cart_product
        FOREIGN KEY (product_id) REFERENCES products(id),

    UNIQUE KEY uk_cart_session_product (session_id, product_id)
);
