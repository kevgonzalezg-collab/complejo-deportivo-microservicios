CREATE TABLE reservas (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          id_usuario BIGINT NOT NULL,
                          id_cancha BIGINT NOT NULL,
                          fecha_hora DATETIME NOT NULL,
                          duracion_minutos INT NOT NULL,
                          estado VARCHAR(50) NOT NULL
);