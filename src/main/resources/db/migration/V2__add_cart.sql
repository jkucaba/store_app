CREATE TABLE cart_items (
    id BINARY(16) NOT NULL,
    session_id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (id),

    CONSTRAINT fk_cart_session
        FOREIGN KEY (session_id) REFERENCES sessions(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_cart_product
        FOREIGN KEY (product_id) REFERENCES products(id),

    UNIQUE KEY uk_cart_session_product (session_id, product_id)
);
