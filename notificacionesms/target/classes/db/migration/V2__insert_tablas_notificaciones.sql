-- 2. Metemos un par de inserts de prueba alineados con tus campos
INSERT INTO notificacion (correo_destino, asunto, mensaje, fecha_envio, estado)
VALUES ('kevis.test@gmail.com', 'Confirmación de Reserva', 'Tu cancha ha sido reservada con éxito para el 2026-07-25.', NOW(), 'ENVIADO');

INSERT INTO notificacion (correo_destino, asunto, mensaje, fecha_envio, estado)
VALUES ('cliente.prueba@correo.com', 'Reserva Pendiente', 'Tienes una reserva pendiente de pago.', NOW(), 'PENDIENTE');