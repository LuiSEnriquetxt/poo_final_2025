CREATE TABLE Usuario(
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    tipo VARCHAR(50)
);

CREATE TABLE Producto(
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(255),
    precio DECIMAL(10,2),
    stock INT
);

CREATE TABLE Carrito(
    id INT IDENTITY(1,1) PRIMARY KEY,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

CREATE TABLE ItemCarrito(
    id INT IDENTITY(1,1) PRIMARY KEY,
    carrito_id INT,
    producto_id INT,
    cantidad INT,
    FOREIGN KEY (carrito_id) REFERENCES Carrito(id),
    FOREIGN KEY (producto_id) REFERENCES Producto(id)
);

CREATE TABLE DeliveryChofer(
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100),
    disponibilidad BIT
);

CREATE TABLE Constancia(
    id INT IDENTITY(1,1) PRIMARY KEY,
    usuario_id INT,
    total DECIMAL(10,2),
    fecha DATETIME,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

CREATE TABLE Reseña(
    id INT IDENTITY(1,1) PRIMARY KEY,
    producto_id INT,
    usuario_id INT,
    comentario VARCHAR(255),
    calificacion INT,
    FOREIGN KEY (producto_id) REFERENCES Producto(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);




INSERT INTO Usuario(nombre, email, password, tipo)
VALUES ('ADMIN', 'ADMIN@ADMIN2.com', '123', 'ADMIN');



ALTER TABLE Usuario
ADD tipo VARCHAR(20);
CREATE LOGIN poo_log WITH PASSWORD = '123';
USE poo;
-- Crea usuario en la base de datos (si no existe)
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'poo_log')
BEGIN
    CREATE USER poo_log FOR LOGIN poo_log;
END
ALTER ROLE db_owner ADD MEMBER poo_log;
-- Listar logins
SELECT name, type_desc FROM sys.server_principals WHERE name = 'poo_log';
CREATE LOGIN poo_log WITH PASSWORD = '123';
USE poo;
-- Listar usuarios de la base
SELECT name FROM sys.database_principals WHERE name = 'poo_log';

-- Si no existe:
CREATE USER poo_log FOR LOGIN poo_log;

-- Dale permisos completos para desarrollo:
ALTER ROLE db_owner ADD MEMBER poo_log;

USE poo;
EXEC sp_addrolemember 'db_owner', 'poo_log';


INSERT INTO usuario (nombre, email, password, tipo)
VALUES ('Administrador', 'admin@ecommerce.com', '123', 'admin');
INSERT INTO usuario (nombre, email, password, tipo)
VALUES ('Luis', 'luis@correo.com', '123', 'cliente');









-- Asegurarte que el login existe
SELECT name, is_disabled FROM sys.sql_logins WHERE name = 'poo_log';

USE master;
GO
SELECT name, type_desc FROM sys.sql_logins WHERE name = 'poo_log';





///////////////////






select * from usuario
INSERT INTO Usuario (nombre, email, password, tipo)
VALUES ('Luis Cliente', 'luis@poo.com', '191936555', 'cliente');

-- Agregar otro usuario tipo admin
INSERT INTO Usuario (nombre, email, password, tipo)
VALUES ('Ana Admin', 'ana@poo.com', '191936555', 'admin');


select * from Producto



USE poo;
GO

-- Productos de ejemplo
INSERT INTO Producto (nombre, descripcion, precio, stock)
VALUES
('Laptop Gamer', 'Laptop potente para videojuegos', 4500, 52),
('Smartphone', 'Teléfono inteligente con 128GB', 1200, 52),
('Auriculares', 'Auriculares con cancelación de ruido', 250,50),
('Teclado Mecánico', 'Teclado mecánico RGB', 150, 54),
('Mouse Gamer', 'Mouse ergonómico para gaming', 100,96),
('Monitor 24"', 'Monitor LED Full HD', 800, 65),
('Impresora', 'Impresora láser multifuncional', 600,54),
('Cámara Web', 'Cámara HD para streaming', 120,545),
('Tablet', 'Tablet 10" con WiFi', 900,51),
('Disco Duro 1TB', 'Disco duro externo 1TB', 200,6551);
ALTER TABLE Producto
ADD stock INT NOT NULL DEFAULT 0;
select * from DeliveryChofer

alter table DeliveryChofer
add telefono int not null default 0;    

INSERT INTO Producto (id, nombre)
VALUES
('123','jose'),
 select * from Usuario


-agregar choferes 
select * from DeliveryChofer 


INSERT INTO DeliveryChofer (nombre, disponibilidad, telefono)
VALUES ('carlos juan adnres ', 1, 987654321);

ALTER TABLE DeliveryChofer
DROP COLUMN id;
ALTER TABLE DeliveryChofer
ADD id INT IDENTITY(1,1) PRIMARY KEY;

select * from reseña

DELETE FROM producto
WHERE id IN (2,3,4,5, 6,7,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,2,29,30);


INSERT INTO Producto (nombre, descripcion, precio, stock)
VALUES
('Laptop Gamer', 'Laptop potente para videojuegos', 4500, 52),
('Smartphone', 'Teléfono inteligente con 128GB', 1200, 52),
('Auriculares', 'Auriculares con cancelación de ruido', 250,50),
('Teclado Mecánico', 'Teclado mecánico RGB', 150, 54),
('Mouse Gamer', 'Mouse ergonómico para gaming', 100,96),
('Impresora', 'Impresora láser multifuncional', 600,54),
('Cámara Web', 'Cámara HD para streaming', 120,545)
select * from USUARIO
(AÑADIMOS PRODUCTOS A LA BIBLIOTECA) 
AÑADIR USUAIRO NUEVO
INSERT INTO USUARIO (NOMBRE, EMAIL, PASSWORD, TIPO)
('jUAN', 'ADMIN@ADMIN.ADMIN', 'ADMIN','ADMIN')





select * from reseña 