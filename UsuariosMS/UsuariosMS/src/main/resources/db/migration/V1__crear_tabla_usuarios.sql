CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          rut VARCHAR(50) NOT NULL UNIQUE,          -- 🎯 ¡COLUMNA AGREGADA AQUÍ!
                          nombre VARCHAR(100) NOT NULL,
                          apellido VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          rol VARCHAR(50) NOT NULL,
                          telefono VARCHAR(20)
);