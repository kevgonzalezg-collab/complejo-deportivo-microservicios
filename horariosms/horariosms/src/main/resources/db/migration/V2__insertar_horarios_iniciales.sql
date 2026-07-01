INSERT INTO horarios (cancha_id, dia_semana, hora_inicio, hora_fin, esta_disponible) VALUES
-- Horarios para la Cancha 1 (ej: Lunes)
(1, 'Lunes', '09:00:00', '10:00:00', TRUE),
(1, 'Lunes', '10:00:00', '11:00:00', TRUE),
(1, 'Lunes', '11:00:00', '12:00:00', FALSE), -- Simulamos una hora ya ocupada

-- Horarios para la Cancha 2 (ej: Martes)
(2, 'Martes', '18:00:00', '19:00:00', TRUE),
(2, 'Martes', '19:00:00', '20:00:00', TRUE),

-- Horarios para la Cancha 3 (ej: Miércoles)
(3, 'Miercoles', '15:00:00', '16:00:00', TRUE),
(3, 'Miercoles', '21:00:00', '22:00:00', TRUE);