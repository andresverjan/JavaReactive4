CREATE SCHEMA tienda AUTHORIZATION root;

CREATE TABLE tienda.Usuarios (
    id VARCHAR(15) PRIMARY KEY, -- Campo id ingresado manualmente por cada usuario (número de cédula)
    nombre VARCHAR(255) NOT NULL, -- Nombre completo del usuario
    email VARCHAR(255) UNIQUE NOT NULL, -- Email único del usuario
    rol VARCHAR(50) NOT NULL CHECK (rol IN ('Cliente', 'Vendedor', 'Ambos')), -- Rol del usuario
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha de última actualización
);

CREATE TABLE tienda.Productos (
    id VARCHAR(10) PRIMARY KEY, -- ID personalizado con el formato PR_1, PR_2, etc.
    nombre VARCHAR(255) NOT NULL, -- Nombre del producto
    precio DECIMAL(10, 2) NOT NULL, -- Precio del producto
    descripcion TEXT, -- Descripción del producto
    imagen_url VARCHAR(255), -- URL de la imagen del producto
    stock INT DEFAULT 0, -- Cantidad disponible en inventario
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha de última actualización
);

CREATE TABLE tienda.CarritoCompras (
    id VARCHAR(10) PRIMARY KEY, -- ID personalizado con el formato CA_1, CA_2, etc.
    usuario_id VARCHAR(15) UNIQUE REFERENCES tienda.usuarios(id), -- Relación de uno a uno con el usuario
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación del carrito
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha de última modificación
);

CREATE TABLE tienda.CarritoProductos (
    carrito_id VARCHAR(10) REFERENCES tienda.CarritoCompras(id) ON DELETE CASCADE, -- Relación con el carrito
    producto_id VARCHAR(10) REFERENCES tienda.Productos(id), -- Relación con el producto
    cantidad INT NOT NULL, -- Cantidad del producto en el carrito
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de adición del producto al carrito
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de última modificación
    PRIMARY KEY (carrito_id, producto_id) -- Clave primaria compuesta
);

CREATE TABLE tienda.OrdenesVentas (
    id VARCHAR(10) PRIMARY KEY, -- ID personalizado con el formato OV_1, OV_2, etc.
    usuario_id VARCHAR(15) REFERENCES tienda.Usuarios(id), -- Relación con el usuario
    total DECIMAL(10, 2) NOT NULL, -- Total de la orden
    estado VARCHAR(50) NOT NULL CHECK (estado IN ('Creada', 'Editada', 'Cancelada')), -- Estado de la orden
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación de la orden
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha de última actualización
);

CREATE TABLE tienda.OrdenesVentasProductos (
    orden_id VARCHAR(10) REFERENCES tienda.OrdenesVentas(id) ON DELETE CASCADE, -- Relación con la orden
    producto_id VARCHAR(10) REFERENCES tienda.Productos(id), -- Relación con el producto
    cantidad INT NOT NULL, -- Cantidad del producto en la orden
    precio_unitario DECIMAL(10, 2) NOT NULL, -- Precio del producto en el momento de la venta
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de última actualización
    PRIMARY KEY (orden_id, producto_id) -- Clave primaria compuesta
);

CREATE TABLE tienda.Empresas (
    id VARCHAR(10) PRIMARY KEY, -- ID personalizado con el formato EM_1, EM_2, etc.
    nombre VARCHAR(255) NOT NULL, -- Nombre de la empresa
    direccion VARCHAR(255), -- Dirección de la empresa
    telefono VARCHAR(20) -- Teléfono de contacto
);

CREATE TABLE tienda.VendedoresEmpresas (
    vendedor_id VARCHAR(15) REFERENCES tienda.Usuarios(id), -- El vendedor es un usuario
    empresa_id VARCHAR(10) REFERENCES tienda.Empresas(id), -- Empresa para la que trabaja el vendedor
    PRIMARY KEY (vendedor_id, empresa_id) -- Clave primaria compuesta
);

CREATE TABLE tienda.OrdenesCompras (
    id VARCHAR(10) PRIMARY KEY, -- ID personalizado con el formato OC_1, OC_2, etc.
    empresa_id VARCHAR(10) REFERENCES tienda.Empresas(id), -- Relación con la empresa que vende productos
    vendedor_id VARCHAR(15) REFERENCES tienda.Usuarios(id), -- Vendedor que gestionó la compra
    total DECIMAL(10, 2) NOT NULL, -- Total de la orden
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha de última actualización
);

CREATE TABLE tienda.OrdenesComprasProductos (
    orden_id VARCHAR(10) REFERENCES tienda.OrdenesCompras(id) ON DELETE CASCADE, -- Relación con la orden de compra
    producto_id VARCHAR(10) REFERENCES tienda.Productos(id), -- Relación con el producto
    cantidad INT NOT NULL, -- Cantidad del producto comprado
    precio_unitario DECIMAL(10, 2) NOT NULL, -- Precio del producto en el momento de la compra
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de última actualización
    PRIMARY KEY (orden_id, producto_id) -- Clave primaria compuesta
);


