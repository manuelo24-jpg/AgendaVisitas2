CREATE TABLE Clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(15),
    email VARCHAR(100),
    fecha_registro DATE,
    notas TEXT,
    ciudad VARCHAR(100),
    pais VARCHAR(100),
    codigo_postal VARCHAR(10),
    fecha_nacimiento DATE,
);

CREATE TABLE Visitas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    notas TEXT,
    duracion INT, 
    tipo_visita ENUM('Consulta', 'Revisión', 'Seguimiento', 'Otro'), 
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);