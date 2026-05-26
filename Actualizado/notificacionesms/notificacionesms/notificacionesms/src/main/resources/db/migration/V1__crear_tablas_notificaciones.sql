CREATE TABLE IF NOT EXISTS notificacion (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            correo_destino VARCHAR(255) NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_envio DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL
    );