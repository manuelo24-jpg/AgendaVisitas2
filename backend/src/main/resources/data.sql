INSERT INTO Cliente (nombre, direccion, telefono, email, fecha_registro, notas, ciudad, pais, codigo_postal, fecha_nacimiento) VALUES
('Juan Pérez', 'Calle Falsa 123', '555-1234', 'juan.perez@example.com', '2023-01-01', 'Cliente regular', 'Ciudad A', 'País A', '12345', '1980-01-01'),
('María López', 'Avenida Siempre Viva 742', '555-5678', 'maria.lopez@example.com', '2023-02-15', 'Cliente VIP', 'Ciudad B', 'País B', '67890', '1985-02-15'),
('Carlos Sánchez', 'Boulevard de los Sueños 456', '555-8765', 'carlos.sanchez@example.com', '2023-03-10', 'Cliente nuevo', 'Ciudad C', 'País C', '54321', '1990-03-10'),
('Ana Torres', 'Calle de la Luna 789', '555-4321', 'ana.torres@example.com', '2023-04-05', 'Cliente frecuente', 'Ciudad D', 'País D', '98765', '1995-04-05'),
('Luis Gómez', 'Avenida del Sol 321', '555-6789', 'luis.gomez@example.com', '2023-05-20', 'Cliente potencial', 'Ciudad E', 'País E', '11223', '1982-05-20'),
('Elena Ruiz', 'Calle del Mar 654', '555-9876', 'elena.ruiz@example.com', '2023-06-15', 'Cliente antiguo', 'Ciudad F', 'País F', '44556', '1978-06-15'),
('Pedro Martínez', 'Calle de la Esperanza 101', '555-1111', 'pedro.martinez@example.com', '2023-07-01', 'Cliente nuevo', 'Ciudad G', 'País G', '55667', '1983-07-01'),
('Laura Fernández', 'Avenida de la Paz 202', '555-2222', 'laura.fernandez@example.com', '2023-08-01', 'Cliente sin visitas', 'Ciudad H', 'País H', '66778', '1992-08-01');

INSERT INTO Visita (cliente_id, fecha, hora, notas, duracion, tipo_visita) VALUES
(1, '2023-04-01', '10:00:00', 'Consulta inicial', 60, 'CONSULTA'),
(1, '2023-05-01', '11:00:00', 'Revisión mensual', 30, 'REVISION'),
(1, '2023-06-01', '09:00:00', 'Seguimiento de progreso', 45, 'SEGUIMIENTO'),
(1, '2023-07-01', '10:30:00', 'Consulta de actualización', 55, 'CONSULTA'),
(2, '2023-04-15', '09:30:00', 'Seguimiento de proyecto', 45, 'SEGUIMIENTO'),
(2, '2023-05-15', '10:00:00', 'Consulta sobre nuevo servicio', 50, 'CONSULTA'),
(2, '2023-06-15', '11:00:00', 'Revisión trimestral', 40, 'REVISION'),
(2, '2023-07-15', '12:00:00', 'Consulta de seguimiento', 35, 'CONSULTA'),
(3, '2023-05-20', '14:00:00', 'Consulta sobre nuevo servicio', 50, 'CONSULTA'),
(3, '2023-06-20', '15:00:00', 'Revisión mensual', 30, 'REVISION'),
(3, '2023-07-20', '16:00:00', 'Seguimiento de progreso', 45, 'SEGUIMIENTO'),
(4, '2023-06-01', '13:00:00', 'Revisión trimestral', 40, 'REVISION'),
(4, '2023-07-01', '14:00:00', 'Consulta de seguimiento', 35, 'CONSULTA'),
(4, '2023-08-01', '15:00:00', 'Revisión anual', 60, 'REVISION'),
(5, '2023-06-10', '15:00:00', 'Consulta de seguimiento', 35, 'CONSULTA'),
(5, '2023-07-10', '16:00:00', 'Revisión mensual', 30, 'REVISION'),
(5, '2023-08-10', '17:00:00', 'Seguimiento de progreso', 45, 'SEGUIMIENTO'),
(6, '2023-07-01', '16:00:00', 'Revisión anual', 60, 'REVISION'),
(6, '2023-08-01', '17:00:00', 'Consulta de actualización', 55, 'CONSULTA'),
(6, '2023-09-01', '18:00:00', 'Seguimiento de resultados', 50, 'SEGUIMIENTO'),
(7, '2023-07-15', '10:30:00', 'Consulta de actualización', 55, 'CONSULTA'),
(7, '2023-08-15', '11:30:00', 'Seguimiento de resultados', 50, 'SEGUIMIENTO'),
(7, '2023-09-15', '12:30:00', 'Consulta sobre mejoras', 45, 'CONSULTA');