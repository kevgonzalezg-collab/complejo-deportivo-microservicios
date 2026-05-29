CREATE TABLE horarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          cancha_id BIGINT NOT NULL,
                          dia_semana VARCHAR(20) NOT NULL, -- Lunes, Martes, etc.
                          hora_inicio TIME NOT NULL,
                          hora_fin TIME NOT NULL,
                          esta_disponible BOOLEAN DEFAULT TRUE
);