CREATE TABLE pagos (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       reserva_id BIGINT NOT NULL,
                       monto DOUBLE NOT NULL,
                       metodo_pago VARCHAR(50) NOT NULL, -- Ej: 'TARJETA', 'TRANSFERENCIA'
                       estado_pago VARCHAR(50) DEFAULT 'PENDIENTE',
                       fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP
);