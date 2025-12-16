CREATE TABLE users (
   id BINARY(16) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password_hash VARCHAR(255) NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   UNIQUE KEY uk_users_email (email)
);

CREATE TABLE sessions (
  id BINARY(16) NOT NULL,
  user_id BINARY(16) NOT NULL,
  expires_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_sessions_user
      FOREIGN KEY (user_id) REFERENCES users(id)
          ON DELETE CASCADE
);

CREATE TABLE products (
  id BINARY(16) NOT NULL,
  title VARCHAR(255) NOT NULL,
  available INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id)
);
