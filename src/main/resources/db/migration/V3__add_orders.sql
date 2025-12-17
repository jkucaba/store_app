CREATE TABLE orders (
    id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    status VARCHAR(32) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
     id BINARY(16) NOT NULL,
     order_id BINARY(16) NOT NULL,
     product_id BINARY(16) NOT NULL,
     quantity INT NOT NULL,
     price_at_purchase DECIMAL(10,2) NOT NULL,
     PRIMARY KEY (id),

     CONSTRAINT fk_order_items_order
         FOREIGN KEY (order_id) REFERENCES orders(id)
             ON DELETE CASCADE
);
