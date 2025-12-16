-- Needs to be run before starting the application, use your own
DROP DATABASE IF EXISTS db_name;
DROP USER IF EXISTS `user_name`@`%`;

CREATE DATABASE db_name
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE USER `user_name`@`%`
    IDENTIFIED WITH caching_sha2_password BY 'user_password';

GRANT ALL PRIVILEGES ON db_name.* TO `user_name`@`%`;
FLUSH PRIVILEGES;