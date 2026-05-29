-- 2. Insertamos el RUT de prueba que dejamos en el ReservasService
INSERT INTO cliente_bloqueado (rut, nombre, motivo, fecha_bloqueo)
VALUES ('12.345.678-9', 'Juan Perez Malo', 'Debe 3 partidos y rompió la red del arco', '2026-05-24');

-- 3. Insertamos otro de respaldo por si quieres hacer más pruebas después
INSERT INTO cliente_bloqueado (rut, nombre, motivo, fecha_bloqueo)
VALUES ('11.111.111-1', 'Cliente Deudor', 'Falta de pago crónica en reservas anteriores', '2026-05-24');