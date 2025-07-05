CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    is_active BOOLEAN,
    token  VARCHAR(36) ,
    token_expiration  TIMESTAMP
);

CREATE TABLE phones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(20),
    city_code VARCHAR(5),
    country_code VARCHAR(5),
    user_id VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES users(id)
);