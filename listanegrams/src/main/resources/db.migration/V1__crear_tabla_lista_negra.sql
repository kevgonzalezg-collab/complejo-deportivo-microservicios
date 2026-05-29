CREATE TABLE IF NOT EXISTS cliente_bloqueado (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 rut VARCHAR(12) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    fecha_bloqueo DATE NOT NULL
    );