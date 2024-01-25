

CREATE PROCEDURE IF NOT EXISTS cargar_datos_iniciales()
BEGIN

    IF NOT EXISTS(
        SELECT*
        FROM logs
    )THEN

    INSERT INTO roles (nombre_rol)
    VALUES
        ('administrativo'),
        ('tecnico'),
        ('cliente');

    INSERT INTO competencias (nombre)
    VALUES
        ('Carpintería'),
        ('Electricista'),
        ('Fontanería'),
        ('Pintura'),
        ('Albañilería'),
        ('Cerrajería'),
        ('Instalación de sistemas eléctricos'),
        ('Reparación de electrodomésticos'),
        ('Fontanería general'),
        ('Reparación de techos');

    INSERT INTO usuarios (nombre_usuario, pass, nombre, apellido, e_mail, activo) 
    VALUES
        ('Admin', '$2a$12$xCUe70osSjaP2vNY9Mh7UO7JTMqzuGKESdnFr97kQKotnEf2ezzYy', 'AdminUno', 'Apellido1', 'admin1@example.com',1),
        ('Cliente1', '$2a$12$8HmHph7gscpGIlhal59z.OAYLveI69WYGPf2hWy7ZaOAkbKSE6/M6', 'ClienteUno', 'Apellido1', 'cliente1@example.com',1),
        ('Cliente2', '$2a$12$kgellXznX29dWABdpuFQn.pbfHpXyWtLNpP5g9bZ5dIDIJiiQoVDK', 'ClienteDos', 'Apellido2', 'cliente2@example.com',1),
        ('Cliente3', '$2a$12$n74FLMUE/nlU3SpdeOZ5dO0PpJnvQBbGZTp7dIuK9YldOTBJ6O6Tu', 'ClienteTres', 'Apellido3', 'cliente3@example.com',1),
        ('Cliente4', '$2a$12$n74FLMUE/nlU3SpdeOZ5dO0PpJnvQBbGZTp7dIuK9YldOTBJ6O6Tu', 'Clientecuatro', 'Apellido4', 'cliente4@example.com',1),
        ('Cliente5', '$2a$12$n74FLMUE/nlU3SpdeOZ5dO0PpJnvQBbGZTp7dIuK9YldOTBJ6O6Tu', 'ClienteCinco', 'Apellido5', 'cliente5@example.com',1),
        ('Tecnico1', '$2a$12$4.C1KftCnMmRjq3z8Czh7e2imKWOVpNxumjTCfZS4kTxAxYEWeswy', 'TécnicoUno', 'Apellido1', 'tecnico1@example.com',1),
        ('Tecnico2', '$2a$12$ZvNQVFmDz/H5xRez7eafY.exLTqke1G2kHHXAhr1UPljz/Ox.KoNW', 'TécnicoDos', 'Apellido2', 'tecnico2@example.com',1),
        ('Tecnico3', '$2a$12$5phTvMScOXothpEHTDcGNeF6/pUybhYroJFO/S5E75TVP4P8TtU.i', 'TécnicoTres', 'Apellido3', 'tecnico3@example.com',1),
        ('Tecnico4', '$2a$12$NzjRJjvVFTy/BSY0CySbjed0sBKApdq7.MDVvUDfeL2tEHC7hRQ3i', 'TécnicoCuatro', 'Apellido4', 'tecnico4@example.com',1),
        ('Tecnico5', '$2a$12$.vBbOl749HEUV1hqLp1xNelxZp.vQdfFgShlV8VqD1mvO1Ko2oaFi', 'TécnicoCinco', 'Apellido5', 'tecnico5@example.com',0);

    INSERT INTO administrativos (fecha_ingreso, nombre_usuario)
    VALUES
        ('2023-01-15', 'Admin');
    

    INSERT INTO clientes (cedula, direccion, telefono, nombre_usuario, horas_tecnico) 
    VALUES
        (111111111, '123 Calle Principal', '1123-4567', 'Cliente1',10),
        (222222222, '456 Avenida Secundaria', '2987-6543', 'Cliente2',10),
        (333333333, '789 Calle de Ejemplo', '2555-5555', 'Cliente3',10),
        (444444444, '321 Calle de Prueba', '2111-1111', 'Cliente4',10),
        (555555555, '654 Avenida de Muestra', '2999-9999', 'Cliente5',10); 

    INSERT INTO tecnicos (telefono, nombre_usuario) 
    VALUES
        ('1111-2233', 'Tecnico1'),
        ('3333-4455', 'Tecnico2'),
        ('5555-6677', 'Tecnico3'),
        ('7777-8899', 'Tecnico4'),
        ('9999-0011', 'Tecnico5');

    INSERT INTO tecnicos_competencias (tecnico_nombre_usuario, competencia_id) 
    VALUES
        ('Tecnico1', 1),
        ('Tecnico1', 2),
        ('Tecnico2', 2),
        ('Tecnico2', 4),
        ('Tecnico3', 5),
        ('Tecnico3', 7),
        ('Tecnico4', 8),
        ('Tecnico5', 9),
        ('Tecnico5', 1),
        ('Tecnico5', 2); 

    INSERT INTO pagos (descripcion, fecha, importe, cliente_nombre_usuario) 
    VALUES
        ('CuotaEnero', '2023-01-15', 100.00, 'Cliente1'),
        ('CuotaFebrero', '2022-02-10', 75.50, 'Cliente2'),
        ('CuotaMarzo', '2021-03-25', 150.25, 'Cliente3'),
        ('CuotaAbril', '2023-04-05', 200.00, 'Cliente1'),
        ('CuotaMayo', '2022-05-30', 50.75, 'Cliente2');


    INSERT INTO contratos (cliente_nombre_usuario, fecha_firma, numero, tiene_imagen)
    VALUES
        ('Cliente1', '2020-10-09', 1, 0),
        ('Cliente2', '2021-10-10', 2, 0),
        ('Cliente2', '2022-10-10', 3, 0),
        ('Cliente3', '2021-10-10', 4, 0),
        ('Cliente4', '2020-10-10', 5, 0);

    INSERT INTO cancelaciones (contrato_numero, fecha, motivo, numero_cancelacion)
    VALUES
        (2, '2022-10-09', 'Motivo A', 1),
        (3, '2023-10-10', 'Motivo B', 2),
        (4, '2022-10-11', 'Motivo C', 3);

    INSERT INTO visitas (cliente_nombre_usuario, descripcion, fechay_hora,hora_inicio, hora_fin)
    VALUES
        ('Cliente1', 'Visita 1', '2021-10-09 08:00', '06:30', '12:30'),
        ('Cliente1', 'Visita 2', '2021-12-09 10:30', '09:30', '15:30'),
        ('Cliente1', 'Visita 3', '2022-10-09 09:00', '08:30', '17:00'),
        ('Cliente2', 'Visita 4', '2021-11-09 08:30', '07:30', '21:30'),
        ('Cliente2', 'Visita 5', '2023-10-08 11:00', '09:30', '14:00'),
        ('Cliente4', 'Visita 6', '2021-10-09 15:30', '09:30', '19:30'),
        ('Cliente4', 'Visita 7', '2023-07-10 10:00', '07:00', '22:00');
    
    INSERT INTO visitas_competencias (competencia_id, visita_numero)
    VALUES
        (1, 1),
        (2, 1),
        (3, 1),
        (1, 2),
        (5, 3),
        (7, 4),
        (10, 5);
    
    INSERT INTO visita_tecnico (tecnico_nombre_usuario, visita_numero)
    VALUES
        ('Tecnico1', 1),
        ('Tecnico2', 2),
        ('Tecnico3', 3),
        ('Tecnico4', 4),
        ('Tecnico2', 6);

    INSERT INTO informes (descripcion, duracion, fechay_hora, horas_extra, visita_numero)
    VALUES
        ('Informe 1', 2, '2021-10-10 08:00', 0, 1),
        ('Informe 1', 3, '2021-12-10 10:30', 0, 2),
        ('Informe 1', 3, '2022-10-10 09:00', 1, 3),
        ('Informe 1', 3, '2021-11-10 08:30', 3, 4);

    INSERT INTO usuarios_roles (rol_nombre_rol, usuario_nombre_usuario)
    VALUES
        ('cliente', 'Cliente1'),
        ('cliente', 'Cliente2'),
        ('cliente', 'Cliente3'),
        ('cliente', 'Cliente4'),
        ('cliente', 'Cliente5'),
        ('tecnico', 'Tecnico1'),
        ('tecnico', 'Tecnico2'),
        ('tecnico', 'Tecnico3'),
        ('tecnico', 'Tecnico4'),
        ('tecnico', 'Tecnico5'),
        ('administrativo' , 'admin');


    INSERT INTO logs (fecha_hora, mensaje)
    VALUES (NOW(), 'Datos iniciales cargados.');

    END IF;
END^;

CALL cargar_datos_iniciales()^;
