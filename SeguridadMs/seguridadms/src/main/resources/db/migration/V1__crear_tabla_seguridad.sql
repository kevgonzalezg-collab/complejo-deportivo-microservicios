CREATE TABLE IF NOT EXISTS credencial (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
    );